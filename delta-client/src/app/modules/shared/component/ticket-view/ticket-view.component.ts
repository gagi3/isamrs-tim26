import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {Ticket} from '../../model/ticket';
import {TicketService} from '../../../moderation/ticket/ticket.service';
import {HeaderComponent} from "../../modules/header/header/header.component";

@Component({
  selector: 'app-ticket-view',
  templateUrl: './ticket-view.component.html',
  styleUrls: ['./ticket-view.component.css']
})
export class TicketViewComponent implements OnInit {
  admin: AirlineCompanyAdmin;
  passenger: Passenger;
  ticket: Ticket = new Ticket();
  read = false;
  errorMessage = '';
  username = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'edit-flight';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private ticketService: TicketService, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) { }

  ngOnInit() {
    this.ticket = this.data;
    this.username = this.tokenStorage.getUsername();
    if (this.tokenStorage.getAuthorities().includes('ROLE_AIRLINECOMPANYADMIN')) {
      console.log('admin');
      this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
        data => {
          this.admin = data;
          this.header.airlineCompanyAdminView();
        }
      );
    } else if (this.tokenStorage.getAuthorities().includes('ROLE_PASSENGER')) {
      console.log('passenger');
      this.profileService.getPassengerByUsername(this.username).subscribe(
        data => {
          this.passenger = data;
          this.header.passengerView();
          if (this.ticket.passenger.username !== this.passenger.username) {
            alert('This is not your ticket!');
            this.dialogRef.close();
          }
        }
      );
    } else {
      alert('You are not signed in. You can only browse flights.');
    }
  }
  cancel() {
    this.dialogRef.close();
  }

}
