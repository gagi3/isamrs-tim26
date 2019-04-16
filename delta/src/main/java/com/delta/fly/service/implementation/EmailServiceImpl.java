package com.delta.fly.service.implementation;

import com.delta.fly.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setFrom(email.getFrom());
        message.setText(email.getMessage());
        message.setSubject(email.getSubject());
        javaMailSender.send(message);
    }

    public String registrationTemplate(String name, String link) {
        return "Dear " + name + ",\n\n"
                + "Thank you for registering on our website.\n\n"
                + "In order to complete your registration, click on the link below.\n\n"
                + "http://localhost:4200/api/user/validate/token=" + link + "\n\n"
                + "Sincerely yours,\n\n"
                + "Delta";
    }

}
