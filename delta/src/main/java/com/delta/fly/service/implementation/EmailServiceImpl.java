package com.delta.fly.service.implementation;

import com.delta.fly.model.Email;
import com.delta.fly.model.Ticket;
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
                + "http://localhost:4200/validate/token/" + link + "\n\n"
                + "Sincerely yours,\n\n"
                + "Delta";
    }

    public String reservationTemplate(String name, Ticket ticket) {
        return "Dear " + name + ",\n\n"
                + "You have reserved a ticket on our website.\n\n"
                + "Details of your reservation are given below.\n\n"
                + "Airline company: " + ticket.getFlight().getAirlineCompany().getName() + "\n"
                + "Airplane: " + ticket.getFlight().getAirplane().getName() + "\n"
                + "Seat: row: " + ticket.getSeat().getRow() + ", column: " + ticket.getSeat().getColumn() + ", class: " + ticket.getSeat().getSeatClass().toString() + "\n"
                + "Departure: " + ticket.getFlight().getDeparture().getThePlace() + ", " + ticket.getFlight().getDeparture().getTheTime().toString() + "\n"
                + "Arrival: " + ticket.getFlight().getArrival().getThePlace() + ", " + ticket.getFlight().getArrival().getTheTime().toString() + "\n"
                + "Distance: " + ticket.getFlight().getDistance() + "\n"
                + transfers(ticket) + "\n"
                + "Price: " + ticket.getPrice() + "\n"
                + "If you want to see your reservations, click on the link below.\n\n"
                + "http://localhost:4200/user-ticket-view\n\n"
                + "Sincerely yours,\n\n"
                + "Delta";
    }

    private String transfers(Ticket ticket) {
        if (ticket.getFlight().getTransfers().size() == 2) {
            return "Transfer point 1: " + ticket.getFlight().getTransfers().get(0).getThePlace() + ", " + ticket.getFlight().getTransfers().get(0).getTheTime().toString() + "\n"
                    + "Transfer point 2: " + ticket.getFlight().getTransfers().get(1).getThePlace() + ", " + ticket.getFlight().getTransfers().get(1).getTheTime().toString();
        } else if (ticket.getFlight().getTransfers().size() == 1) {
            return "Transfer point: " + ticket.getFlight().getTransfers().get(0).getThePlace() + ", " + ticket.getFlight().getTransfers().get(0).getTheTime().toString();
        } else {
            return "You will fly straight to your destination, with no transfers.";
        }
    }

}
