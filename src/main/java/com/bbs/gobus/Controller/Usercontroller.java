package com.bbs.gobus.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.BusDetails;
import com.bbs.gobus.entity.User;
import com.bbs.gobus.repository.BookingRepository;
import com.bbs.gobus.repository.BusRepository;
import com.bbs.gobus.repository.UserRepository;
import com.bbs.gobus.service.AuthService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/user")
public class Usercontroller {

    private final UserRepository userRepository;

      @Autowired
      AuthService authservice;

      @Autowired
      BusRepository busRepository;

      @Autowired
      BookingRepository bookingRepository;

    Usercontroller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String userHome(HttpSession session,ModelMap map) {
        // authservice.getloggedInUser(session);
            User user = authservice.getloggedInUser(session);
            map.put("username", user.getUsername());
            return "user-home.html";
    }
    @GetMapping("/search")
    public String userhomepage(){

        return "user-home.html";
    }
    @GetMapping("/home1")
    public String home() {
        return "redirect:/user/";
    }
    
    @PostMapping("/search")
    public String postMethodName(@RequestParam String BoardingPoint,@RequestParam String DropPoint, @RequestParam String date, ModelMap map) {
         List<BusDetails> bus = busRepository.findByBoardingPointAndDropPointAndDate(BoardingPoint, DropPoint,date);
         
         map.put("busList", bus);
        return "search-results.html"; 
      
        
    }
    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, ModelMap map) {
        User user = authservice.getloggedInUser(session);
       
        Long userId = user.getId();
    //    Optional<Booking> bookings = bookingRepository.findById(userId);
    //     map.put("bookings", bookings);
    System.out.println("userId: " + userId);
    List<Booking> booking = bookingRepository.findByUserId(user.getId());
if (booking.isEmpty()) {
        map.put("error", "No bookings found.");
    } else {
        map.put("bookings", booking);
    }
        return "user-bookings.html";
    }
     
  @GetMapping("/ticket/{id}")
public String viewTicket(@PathVariable Long id, ModelMap model) {
    Booking booking = authservice.getBookingById(id);
    model.addAttribute("booking", booking);
    return "booking"; // ticket.html
}


    
}
