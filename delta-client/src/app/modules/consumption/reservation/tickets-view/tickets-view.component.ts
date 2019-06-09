import {Component, Inject, OnInit} from '@angular/core';
import {Flight} from '../../../shared/model/flight';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {Ticket} from '../../../shared/model/ticket';
import {DiscountTicketsDTO} from '../../../moderation/ticket/discount-tickets-dto';
import {FlightService} from '../../../moderation/flight/flight.service';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {TicketService} from '../../../moderation/ticket/ticket.service';

@Component({
  selector: 'app-tickets-view',
  templateUrl: './tickets-view.component.html',
  styleUrls: ['./tickets-view.component.css']
})
export class TicketsViewComponent implements OnInit {
  dto: DiscountTicketsDTO = new DiscountTicketsDTO();
  discounted: Ticket[] = [];
  username = '';
  passenger: Passenger = new Passenger();
  flight: Flight = new Flight();
  added = false;
  failed = false;
  errorMessage = '';
  row = 0;
  col = 0;
  tix = [];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: TicketService, private flightService: FlightService, private tokenStorage: TokenStorageService,
              private profileService: ProfileService) { }
  cancel() {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.dialogRef.updateSize('40%', '80%');
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
      }
    );
    this.service.getDiscounted().subscribe(
      data => {
        this.discounted = data;
      }
    );
    this.flight = this.data.flight;
    console.log(this.data);
    console.log(this.flight);
    this.reread();
    this.refresh();
  }
  reread() {
    this.calcRows();
    this.mapTickets();
    // console.log(this.flight);
  }
  onSubmit() {
    this.service.reserve(this.dto).subscribe(
      data => {
        alert('Ticket reservation was successful!');
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
            if (ticket.deleted === false) {
              rowSeats.push(ticket);
            }
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
    for (let i = 0; i < this.tix.length; i++) {
      for (let j = 0; j < this.tix[i].length; j++) {
        if (this.tix[i][j].seat.seatClass.toString() === 'ECONOMY') {
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.border = '3px solid #993399';
        } else if (this.tix[i][j].seat.seatClass.toString() === 'BUSINESS') {
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.border = '3px solid #3385ff';
        } else if (this.tix[i][j].seat.seatClass.toString() === 'FIRST') {
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.border = '3px solid #00ffa5';
        } else {
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.border = '3px solid #f1f1f1';
        }
        if (!this.checkPass(this.tix[i][j])) {
          console.log('undefined');
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.pointerEvents = 'none';
        }
        if (!this.checkDisc(this.tix[i][j])) {
          console.log('exists');
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.pointerEvents = 'none';
        }
      }
    }
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
    console.log(this.dto.tickets);
    console.log(ticket);
    // this.refresh();
    console.log(this.checkPass(ticket));
    if (!this.checkPass(ticket)) {
      alert('Ticket already reserved!');
    } else if (!this.checkDisc(ticket)) {
      alert('Ticket already discounted!');
    } else if (ticket.deleted) {
      alert('Ticket deleted!');
    } else if (!this.checkDTO(ticket)) {
      document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = '#f1f1f1';
      const index = this.dto.tickets.indexOf(ticket);
      if (index !== -1) {
        this.dto.tickets.splice(index, 1);
      }
    } else {
      this.dto.tickets.push(ticket);
      if (ticket.seat.seatClass.toString() === 'ECONOMY') {
        document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = '#993399';
      } else if (ticket.seat.seatClass.toString() === 'BUSINESS') {
        document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = '#3385ff';
      } else if (ticket.seat.seatClass.toString() === 'FIRST') {
        document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = '#00ffa5';
      } else {
        document.getElementById('seat-label-' + ticket.seat.row + '-' + ticket.seat.column).style.background = '#f1f1f1';
      }
    }
  }
  checkPass(ticket: Ticket): boolean {
    return ticket.passenger === null;
  }
  checkDisc(ticket: Ticket): boolean {
    for (const t of this.discounted) {
      if (t.id === ticket.id) {
        return false;
      }
    }
    return true;
  }
  checkDTO(ticket: Ticket): boolean {
    for (const t of this.dto.tickets) {
      if (t.id === ticket.id) {
        return false;
      }
    }
    return true;
  }

}
