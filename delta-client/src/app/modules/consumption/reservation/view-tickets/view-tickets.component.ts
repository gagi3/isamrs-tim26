import {Component, OnInit, ViewChild} from '@angular/core';
import {Ticket} from '../../../shared/model/ticket';
import {Flight} from '../../../shared/model/flight';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {TicketService} from '../../../moderation/ticket/ticket.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog} from '@angular/material';
import {FlightService} from '../../../moderation/flight/flight.service';
import {PlaceAndTime} from '../../../shared/model/place-and-time';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

@Component({
  selector: 'app-view-tickets',
  templateUrl: './view-tickets.component.html',
  styleUrls: ['./view-tickets.component.css']
})
export class ViewTicketsComponent implements OnInit {
  tickets: Ticket[] = [];
  flights: Flight[] = [];
  username = '';
  passenger: Passenger;
  @ViewChild('header') header: HeaderComponent;
  showView = 'tickets-view';

  constructor(private service: TicketService, private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog, private flightService: FlightService) {
  }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
        this.header.passengerView();
      }
    );
    this.profileService.getTickets().subscribe(
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
  }

  setData(tickets: Ticket[]) {
    this.tickets = tickets;
  }

  mapTF() {
    for (const flight of this.flights) {
      for (const t of flight.tickets) {
        for (const ticket of this.tickets) {
          if (ticket.id === t.id) {
            ticket.flight = flight;
          }
        }
      }
    }
    this.setEmpty();
  }

  setEmpty() {
    for (const ticket of this.tickets) {
      if (ticket.flight.transfers[0] === undefined) {
        ticket.flight.transfers[0] = new PlaceAndTime();
      }
      if (ticket.flight.transfers[1] === undefined) {
        ticket.flight.transfers[1] = new PlaceAndTime();
      }
    }
  }

  cancel(ticket: Ticket) {
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      this.service.cancel(ticket).subscribe(
        data => {
          if (data === true) {
            alert('Your reservation was cancelled!');
            this.router.navigateByUrl('tickets');
          } else {
            console.log(data);
            alert('Your reservation was not cancelled!');
          }
        }
      );
    }
  }

  onNavigate(feature: string) {
    console.log(feature);
    this.showView = feature;
    if (feature === 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['']);
      window.alert('Successfully Logged out!');
    }
  }

}
