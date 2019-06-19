import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Flight} from '../../../shared/model/flight';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {Ticket} from '../../../shared/model/ticket';
import {DiscountTicketsDTO} from '../../../moderation/ticket/discount-tickets-dto';
import {FlightService} from '../../../moderation/flight/flight.service';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {TicketService} from '../../../moderation/ticket/ticket.service';
import {TicketReservationComponent} from '../ticket-reservation/ticket-reservation.component';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';

@Component({
  selector: 'app-tickets-view',
  templateUrl: './tickets-view.component.html',
  styleUrls: ['./tickets-view.component.css']
})
export class TicketsViewComponent implements OnInit {
  dto: DiscountTicketsDTO = new DiscountTicketsDTO();
  discountedTickets: Ticket[] = [];
  selectedTicket: Ticket = new Ticket();
  username = '';
  passenger: Passenger = new Passenger();
  flight: Flight = new Flight();
  added = false;
  failed = false;
  errorMessage = '';
  row = 0;
  col = 0;
  reserved = false;
  tix = [];
  @ViewChild('header') header: HeaderComponent;
  showView = 'tickets-view';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: TicketService, private flightService: FlightService, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) {
  }

  cancel() {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.dialogRef.updateSize('40%', '80%');
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
        this.header.passengerView();
      }
    );
    this.service.getDiscounted().subscribe(
      data => {
        this.discountedTickets = data;
      }
    );
    this.flight = this.data.flight;
    this.reread();
    this.refresh();
  }

  reread() {
    this.calcRows();
    this.mapTickets();
    console.log(this.tix);
    // this.refresh();
    // console.log(this.flight);
  }

  onSubmit() {
    alert('Reserved.');
    this.router.navigateByUrl('');
  }

  mapTickets() {
    console.log(this.flight.tickets);
    console.log(this.flight.airplane.seats);
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
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.pointerEvents = 'none';
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.background = 'gray';
        }
        if (!this.checkDisc(this.tix[i][j])) {
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.pointerEvents = 'none';
          document.getElementById('seat-label-' + this.tix[i][j].seat.row + '-' + this.tix[i][j].seat.column).style.background = 'gray';
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
    if (!this.checkPass(ticket)) {
      alert('Ticket already reserved!');
    } else if (!this.checkDisc(ticket)) {
      alert('Ticket discounted!');
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
      this.selectedTicket = ticket;
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

  reservation(ticket: Ticket) {
    if (this.passenger.id !== undefined) {
      const dialogConfig = new MatDialogConfig();
      this.dialogRef.updateSize('100%', '100%');
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
        id: 1,
        ticket
      };
      const dialogRef = this.dialog.open(TicketReservationComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        result => {
          console.log('Dialog closed.');
          console.log(result);
          location.reload();
        }
      );
    } else {
      console.log('Passenger doesn\'t exist.');
    }
  }

  checkPass(ticket: Ticket): boolean {
    return ticket.passenger === null;
  }

  checkDisc(ticket: Ticket): boolean {
    for (const t of this.discountedTickets) {
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
