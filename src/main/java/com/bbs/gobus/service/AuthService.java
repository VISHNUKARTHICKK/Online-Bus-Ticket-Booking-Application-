package com.bbs.gobus.service;

import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.bbs.gobus.dto.UserDto;
import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.BusDetails;
import com.bbs.gobus.entity.Passenger;
import com.bbs.gobus.entity.User;

import jakarta.servlet.http.HttpSession;


public interface AuthService {

    String registerUser(UserDto userDto, BindingResult result, HttpSession session);

    String register(UserDto userdto, ModelMap map);

    String confirmOtp(int otp, HttpSession session);

    String login(String username, String password,HttpSession session);
 
    User getloggedInUser(HttpSession session);

    User getloggedInAdmin(HttpSession session);

    String logout(HttpSession session);

    void addBus(BusDetails busDetails);

    void removeBus(Long id);

    List<BusDetails> viewbus();

    void edit(Long id,ModelMap map);

    void update(BusDetails busDetails, ModelMap map);

    void delete(Long id, ModelMap map);

    void saveBooking(Booking booking, List<Passenger> passengerList);

    Booking saveBooking(Booking booking);

    void bookingdetails(ModelMap map);

  


}
