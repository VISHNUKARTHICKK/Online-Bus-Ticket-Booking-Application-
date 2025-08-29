package com.bbs.gobus.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bbs.gobus.entity.Booking;
import com.bbs.gobus.entity.Passenger;


import jakarta.mail.internet.MimeMessage;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
   TemplateEngine templateEngine;

   

     @Value("${spring.mail.username}")
    String fromEmail;

    public void sendTicketEmail(Booking booking, byte[] pdfBytes) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Passenger p = new Passenger();
        

        helper.setTo(booking.getEmail());
        helper.setFrom(fromEmail,"GoBuss App");
        helper.setSubject("Your GoBus Ticket");

Context context = new Context();
context.setVariable("booking", booking);
context.setVariable("passenger", p);

         String body=templateEngine.process("ticket.html",context);
       helper.setText(body,true);


        // helper.setText("Dear Customer,\n\nPlease find attached your GoBus ticket.\n\nThank you for booking with us!");

        helper.addAttachment("GoBus_Ticket.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}

