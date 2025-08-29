package com.bbs.gobus.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.BusDetails;
import com.bbs.gobus.entity.Passenger;
import com.bbs.gobus.entity.User;
import com.bbs.gobus.helper.EmailService;
import com.bbs.gobus.helper.TicketService;
import com.bbs.gobus.repository.BookingRepository;
import com.bbs.gobus.repository.BusRepository;
import com.bbs.gobus.repository.PassengerRepository;
import com.bbs.gobus.repository.UserRepository;
import com.bbs.gobus.service.AuthService;

import jakarta.servlet.http.HttpSession;



@Controller
public class BookingController {

    
@Autowired
AuthService authService;

  @Autowired
  BusRepository busRepository;
  @Autowired
BookingRepository bookingRepository;

@Autowired
PassengerRepository passengerRepository;

@Autowired
TicketService ticketService;

@Autowired
EmailService emailService;

@Autowired
UserRepository userRepository;

   @GetMapping("/book/{id}")
public String showBookingForm(@PathVariable Long id, ModelMap map, HttpSession session) {
    BusDetails bus = busRepository.findById(id).orElseThrow(() -> new RuntimeException("Bus not found"));

   User user = authService.getloggedInUser(session);
  // get logged-in user

    // System.out.println("Bus in booking form: " + bus.getSeats());
    Long seats=bus.getSeats();
    Long price=bus.getPrice();

    
    System.out.println("Bus seats: " + seats);
    System.out.println("Bus Price: " + price);

    
    Long userid=user.getId();

    Booking booking = new Booking();
    booking.setBusName(bus.getBusName());
    booking.setBoardingPoint(bus.getBoardingPoint());
    booking.setDropPoint(bus.getDropPoint());
    booking.setDate(bus.getDate());


    map.addAttribute("bus", bus);
    map.addAttribute("booking", booking);
    map.addAttribute("user", userid); // pass user to the view
    
    System.out.println("User in booking form: " + userid);

    return "traveller-details.html";  // bookingForm.html
}
 @PostMapping("/confirmBooking")
    public String confirmBooking(@RequestParam String email,
                                 @RequestParam String phone,
                                 @RequestParam int passengers,
                                 @RequestParam String busName,
                                 @RequestParam String boardingPoint,
                                 @RequestParam String dropPoint,
                                 @RequestParam String date,
                                 @RequestParam List<String> passengerNames,
                                 @RequestParam List<Integer> ages,
                                 @RequestParam List<String> genders,
                                 @RequestParam Long userid,
                                 ModelMap model) throws Exception {

        // Create new booking
        Booking booking = new Booking();
        booking.setEmail(email);
        booking.setPhone(phone);
        booking.setPassengers(passengers);
        booking.setBusName(busName);
        booking.setBoardingPoint(boardingPoint);
        booking.setDropPoint(dropPoint);
        booking.setDate(date);
        booking.setUser(userRepository.findById(userid).orElse(null));
       

        // Add passenger list
        List<Passenger> passengerList = new ArrayList<>();
        
    for (int i = 0; i < passengers; i++) {
        Passenger p = new Passenger();
        p.setName(passengerNames.get(i));
        p.setAge(ages.get(i));
        p.setGender(genders.get(i));
        p.setBooking(booking);  // link back
        passengerList.add(p);
    }

        booking.setPassengerList(passengerList);



        // Save booking
        bookingRepository.save(booking);
// generate ticket PDF
    byte[] pdf = ticketService.generateTicketPdf(booking);

    // send email
    emailService.sendTicketEmail(booking, pdf);

    // Long busseats = busDetails.getSeats();
    // busseats=busseats-passengers;
    

        // Send data to success page
        model.put("booking", booking);

        return "success.html";  // after booking success page
    }

}
