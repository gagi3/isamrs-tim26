import {Component, Inject, OnInit} from '@angular/core';
import {DiscountTicketsDTO} from '../discount-tickets-dto';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Flight} from '../../../shared/model/flight';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {TicketService} from '../ticket.service';
import {FlightService} from '../../flight/flight.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {Ticket} from '../../../shared/model/ticket';

@Component({
  selector: 'app-discount-tickets',
  templateUrl: './discount-tickets.component.html',
  styleUrls: ['./discount-tickets.component.css']
})
export class DiscountTicketsComponent implements OnInit {
  dto: DiscountTicketsDTO = new DiscountTicketsDTO();
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  flight: Flight = new Flight();
  added = false;
  failed = false;
  errorMessage = '';
  row = 0;
  col = 0;
  tix = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: TicketService, private flightService: FlightService, private tokenStorage: TokenStorageService,
              private adminService: ProfileService) { }
  cancel() {
    this.router.navigateByUrl('');
  }
  ngOnInit() {
    // this.dialogRef.updateSize('40%', '80%');
    this.username = this.tokenStorage.getUsername();
    this.adminService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
      }
    );
    // this.flight = this.data;
    this.flightService.getOne(11).subscribe(
      data => {
        this.flight = data;
        this.getFlight(data);
      }
    );
  }
  getFlight(flight: Flight) {
    this.flight = flight;
    this.reread();
  }
  reread() {
    this.calcRows();
    this.mapTickets();
    console.log(this.flight);
  }
  onSubmit() {
    this.service.discount(this.dto).subscribe(
      data => {
        alert('Discounting tickets was successful!');
        this.added = true;
        this.failed = false;
        this.router.navigateByUrl('');
      }, error => {
        console.log(error);
        this.errorMessage = error.errorMessage;
        this.added = false;
        this.failed = true;
        alert(this.errorMessage);
      }
    );
  }
  mapTickets() {
    const row = [];
    let rowSeats = [];
    for (let i = 0; i < this.row; i++) {
      for (let j = 0; j < this.col; j++) {
        for (const ticket of this.flight.tickets) {
          if (ticket.seat.row === i + 1 && ticket.seat.column === j + 1) {
            rowSeats.push(ticket);
          }
        }
      }
      row.push(rowSeats);
      rowSeats = [];
    }
    this.tix = row;
  }

  calcRows() {
    for (const seat of this.flight.airplane.seats) {
      if (this.row < seat.row) {
        this.row = seat.row;
      }
      if (this.col < seat.column) {
        this.col = seat.column;
      }
    }
  }

  refresh() {
    if (this.col === 6) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '11.1%');
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '11.1%');
    } else if (this.col === 5) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '14.28571428571429%');
      document.getElementsByClassName('seat').item(3).setAttribute('margin-right', '14.28571428571429%');
    } else if (this.col === 4) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '20.28571428571429%');
    } else if (this.col === 3) {
      document.getElementsByClassName('seat').item(1).setAttribute('margin-right', '26.28571428571429%');
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '26.28571428571429%');
    } else if (this.col === 2) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '32.28571428571429%');
    } else if (this.col === 1) {
      document.getElementsByClassName('seat').item(0).setAttribute('margin-right', '44.28571428571429%');
    }
  }
  seatAction(ticket: Ticket) {
    console.log(ticket);
    document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = 'red';
    this.dto.tickets.push(ticket);
  }

}
