package com.bbs.gobus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.BusDetails;
import com.bbs.gobus.entity.User;
import com.bbs.gobus.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class Admincontroller {

    @Autowired
    AuthService authService;

    @GetMapping("/home")
    public String adminhome(HttpSession session,ModelMap map) {
        // authService.getloggedInAdmin(session);
         User user = authService.getloggedInAdmin(session);
            map.put("username", user.getUsername());
            
        return "admin-home.html";
    }
    @GetMapping("/addbus")
    public String addbus()
{
    return "add-bus.html";
}
    @PostMapping("/add-bus")
    public String addBus(@ModelAttribute BusDetails busDetails) {

        authService.addBus(busDetails);
        return "redirect:/admin/home";
        
    }

    @GetMapping("/viewbus")
    public String viewbus(ModelMap map)
    {
        List<BusDetails> bus = authService.viewbus();
         map.put("busList", bus);
        return "viewbus.html";
    }
    @GetMapping("/edit/{id}")
    public String editbus(@PathVariable Long id,ModelMap map)
    {
        authService.edit(id,map);
        return "edit.html";
    }
    @PostMapping("/updatebus")
    public String update(@ModelAttribute BusDetails busDetails,ModelMap map)
    {
        authService.update(busDetails,map);
        return viewbus(map);
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,ModelMap map)
    {
         authService.delete(id,map);
         return viewbus(map);
    }
   @GetMapping("/booking")
    public String bookingdetails(ModelMap map)
    {
     authService.bookingdetails(map);
         return "admin-booking.html";
    }
      @GetMapping("/ticket/{id}")
public String viewTicket(@PathVariable Long id, ModelMap model) {
    Booking booking = authService.getBookingById(id);
    model.addAttribute("booking", booking);
    return "booking"; // ticket.html
}
}
