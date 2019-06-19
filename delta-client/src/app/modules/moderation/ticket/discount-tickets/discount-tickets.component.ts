import {Component, Inject, OnInit, ViewChild} from '@angular/core';
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
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

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
  @ViewChild('header') header: HeaderComponent;
  showView = 'discount-tickets';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: TicketService, private flightService: FlightService, private tokenStorage: TokenStorageService,
              private adminService: ProfileService) {
  }

  cancel() {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.dialogRef.updateSize('40%', '80%');
    this.username = this.tokenStorage.getUsername();
    this.adminService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
      }
    );
    this.flight = this.data.flight;
    this.reread();
    this.refresh();
  }

  reread() {
    this.calcRows();
    this.mapTickets();
    // console.log(this.flight);
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
        }
        if (!this.checkDisc(this.tix[i][j])) {
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
    // this.refresh();
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
    for (const t of this.admin.airlineCompany.discountedTickets) {
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
