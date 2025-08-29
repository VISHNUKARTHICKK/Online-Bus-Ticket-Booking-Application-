package com.bbs.gobus.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bbs.gobus.dto.UserDto;
import com.bbs.gobus.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class Buscontroller {
    @Autowired
    AuthService authService;

@GetMapping("/")
public String home() {
    return "main.html";
}
@GetMapping("/login")
public String loadlogin() {
    return "login.html";
}
@GetMapping("/register")
public String register(UserDto userdto,ModelMap map) {
   return authService.register(userdto, map);
}
@PostMapping("/register")
public String loadRegister(@ModelAttribute @Valid UserDto userDto, BindingResult result,HttpSession session) { 
 
    return authService.registerUser(userDto, result,session);
}
@GetMapping("/otp")
public String loadOtpPage(UserDto userDto, ModelMap map) {
    return "otp.html";
}
@PostMapping("/otp")
public String verifyOtp(@RequestParam int otp,HttpSession session) {
    return authService.confirmOtp(otp,session);
}
@PostMapping("/login")
public String login(@RequestParam String user,@RequestParam String password,HttpSession session) {
    
    
    return authService.login(user,password,session);
}
@PostMapping("/logout")
public String logout(HttpSession session) {
    
    return authService.logout(session);

}
@GetMapping("/contact")
public String contact() {
    return "contact.html";
}
}