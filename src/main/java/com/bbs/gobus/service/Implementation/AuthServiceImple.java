package com.bbs.gobus.service.Implementation;

import java.util.List;
import java.util.Random;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.bbs.gobus.dto.Role;
import com.bbs.gobus.dto.UserDto;
import com.bbs.gobus.entity.AES;
import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.BusDetails;
import com.bbs.gobus.entity.Passenger;
import com.bbs.gobus.entity.User;
import com.bbs.gobus.helper.EmailHelper;
import com.bbs.gobus.repository.BookingRepository;
import com.bbs.gobus.repository.BusRepository;
import com.bbs.gobus.repository.UserRepository;
import com.bbs.gobus.service.AuthService;

import exception.UserNotLoggedInException;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthServiceImple implements AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Random random;

    @Autowired
    EmailHelper emailHelper;

    @Autowired
    BusRepository busRepository;

    public String register(UserDto userdto, ModelMap map) {
         map.put("userdto", userdto);
         return "register.html";
    }

    public String registerUser(UserDto userDto, BindingResult result, HttpSession session) {
    
    if (!userDto.getPassword().equals(userDto.getConfirmPassword()))
    {
          result.rejectValue("confirmPassword", "error.confirmPassword", "* Password and Confirm Password must be same");    
    } 
    if(userRepository.existsByEmail(userDto.getEmail()))
    {
        result.rejectValue("email", "error.email", "* Email already exists");
    }
     if(userRepository.existsByMobile(userDto.getMobile()))
    {
        result.rejectValue("mobile", "error.mobile", "* Mobile already exists");
    }

    
    if (result.hasErrors()) {
        return "register.html";
        
    }else{
        int otp = otp();
        emailHelper.sendOtp(otp,userDto);
        session.setAttribute("otp", otp);
        session.setAttribute("userDto", userDto);
        return "/otp.html";
    }
    
    }
   private int otp(){
    return random.nextInt(100000,1000000); 
   }

   @Override
   public String confirmOtp(int otp,HttpSession session) {
    
    int genOtp = (int) session.getAttribute("otp");
    UserDto dto = (UserDto) session.getAttribute("userDto");
    if(genOtp==otp)
    {
         User user = new User(dto);
         userRepository.save(user);
        session.setAttribute("pass", "Account Create Success");
         return "login.html";
    }
    
    else{
        session.setAttribute("fail","invaild OTP,Try Again");
        return "redirect:/otp";
    }
   }

   @Override
   public String login(String user, String password,HttpSession session) {
    User u=null;
    try{
        Long mobile=Long.parseLong(user);
        u= userRepository.findByMobile(mobile);
    }
    catch(NumberFormatException e)
    {
        u = userRepository.findByEmail(user);
    }
     if(u==null){
        session.setAttribute("fail","Invaild User or Password");
        return "redirect:/login";
        
     }
     else{
        if(AES.decrypt(u.getPassword()).equals(password))
        {
            session.setAttribute("user", u);
            if (u.getRole().equals(Role.ADMIN)) {
                session.setAttribute("pass", " Welcome Admin ");
                  return "redirect:/admin/home";
            }
            else{
                session.setAttribute("pass", "Login Success as User");
                return "redirect:/user/home";
            }
          
        }
        else{
            session.setAttribute("fail","Invaild User or Password");
            return "redirect:/login";
        }
     }
   }

   @Override
   public User getloggedInUser(HttpSession session) {
     User user = (User) session.getAttribute("user");
     if (user!=null) {
        
    
     if(user.getRole().equals(Role.USER)) 
         return user;
     
    }
     throw new UserNotLoggedInException();
     
   }

   @Override
   public User getloggedInAdmin(HttpSession session) {
     User user = (User) session.getAttribute("user");
     if (user!=null) {
        
   
     if(user.getRole().equals(Role.ADMIN)) 
         return user;
     
    }
    
     throw new UserNotLoggedInException();
     
   }

   @Override
   public String logout(HttpSession session) {
   session.removeAttribute("user");
   session.setAttribute("pass", "Logout Successfully");
   return "redirect:/login";
   }

   @Override
   public void addBus(BusDetails busDetails) {
   busRepository.save(busDetails);
    
   }

   @Override
   public void removeBus(Long id) {
    busRepository.deleteById(id);
   }

   @Override
   public List<BusDetails> viewbus() {
    return busRepository.findAll();
   }

   @Override
   public void edit(Long id,ModelMap map) {
    BusDetails bus=busRepository.findById(id).get();
    map.put("bus",bus);

   }

   @Override
   public void update(BusDetails busDetails, ModelMap map) {
    busRepository.save(busDetails);
    map.put("pass"," details updated successfully");
    
   }

   @Override
   public void delete(Long id, ModelMap map) {
    busRepository.deleteById(id);
    map.put("pass", id +" details deleted successfully");
   }
   
     private final BookingRepository bookingRepository;

    public AuthServiceImple(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void saveBooking(Booking booking, List<Passenger> passengerList) {
       
    }

    @Override
    public void bookingdetails(ModelMap map) {
        List<Booking> booking = bookingRepository.findAll();
         map.put("bookings", booking);
    }

    @Override
    public Booking getBookingById(Long id) {
      Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }
}


