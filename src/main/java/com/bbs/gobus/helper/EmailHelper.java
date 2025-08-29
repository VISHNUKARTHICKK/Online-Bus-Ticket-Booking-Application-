package com.bbs.gobus.helper;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.bbs.gobus.dto.UserDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

@Service
public class EmailHelper {

    @Autowired
    JavaMailSender mailsender;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    String fromEmail;


   public void sendOtp(int otp, UserDto userDto){
    MimeMessage message=mailsender.createMimeMessage();
    MimeMessageHelper helper=new MimeMessageHelper(message);

    try {
        helper.setTo(userDto.getEmail());
         helper.setFrom(fromEmail,"GoBuss App");
         helper.setSubject("GoBuss OTP");

    Context context = new Context();
    context.setVariable("otp", otp);
    context.setVariable("username", userDto.getName());
     

       String body=templateEngine.process("email-template.html",context);
       helper.setText(body,true);
       mailsender.send(message);
        // System.out.println("Email sent successfully with OTP: " + otp);
    } catch (MessagingException | UnsupportedEncodingException e) {
        System.out.println(otp);
        System.out.println("Error while sending email: ");

    }
   
  
   }
}
