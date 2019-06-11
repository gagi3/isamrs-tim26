import { Component, OnInit } from '@angular/core';
import {TicketService} from '../../../moderation/ticket/ticket.service';
import {FlightService} from '../../../moderation/flight/flight.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog} from '@angular/material';
import {Ticket} from '../../../shared/model/ticket';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {PlaceAndTime} from '../../../shared/model/place-and-time';
import {Flight} from '../../../shared/model/flight';

@Component({
  selector: 'app-discounted-tickets-view',
  templateUrl: './discounted-tickets-view.component.html',
  styleUrls: ['./discounted-tickets-view.component.css']
})
export class DiscountedTicketsViewComponent implements OnInit {
  discountedTickets: Ticket[] = [];
  flights: Flight[] = [];
  username = '';
  passenger: Passenger;

  constructor(private service: TicketService, private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog, private flightService: FlightService) { }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
      }
    );
    this.service.getDiscounted().subscribe(
      data => {
        this.setData(data);
      }
    );
    this.flightService.get().subscribe(
      data => {
        this.setFlights(data);
      }
    );
  }
  setFlights(flights: Flight[]) {
    this.flights = flights;
    this.mapTF();
    this.setEmpty();
    console.log(this.discountedTickets);
  }
  setData(tickets: Ticket[]) {
    this.discountedTickets = tickets;
  }
  mapTF() {
    for (const flight of this.flights) {
      for (const t of flight.tickets) {
        for (const ticket of this.discountedTickets) {
          if (ticket.id === t.id) {
            ticket.flight = flight;
          }
        }
      }
    }
    this.setEmpty();
  }
  setEmpty() {
    for (const ticket of this.discountedTickets) {
      if (ticket.flight.transfers[0] === undefined) {
        ticket.flight.transfers[0] = new PlaceAndTime();
      }
      if (ticket.flight.transfers[1] === undefined) {
        ticket.flight.transfers[1] = new PlaceAndTime();
      }
    }
  }
  quickReserve(ticket: Ticket) {
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      this.service.quickReserve(ticket, 0).subscribe(
        data => {
          if (data.passenger.id === this.passenger.id) {
            alert('Reservation successful!');
            this.router.navigateByUrl('');
          } else {
            console.log(data);
            alert('Reservation unsuccessful!');
          }
        }
      );
    }
  }

}
