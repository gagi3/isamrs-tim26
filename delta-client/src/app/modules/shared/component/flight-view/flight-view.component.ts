import { Component, OnInit } from '@angular/core';
import {Flight} from '../../model/flight';
import {FlightService} from '../../../moderation/flight/flight.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {EditFlightComponent} from '../../../moderation/flight/edit-flight/edit-flight.component';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {PlaceAndTime} from '../../model/place-and-time';
import {DiscountTicketsComponent} from "../../../moderation/ticket/discount-tickets/discount-tickets.component";
import {TicketsViewComponent} from "../../../consumption/reservation/tickets-view/tickets-view.component";

@Component({
  selector: 'app-flight-view',
  templateUrl: './flight-view.component.html',
  styleUrls: ['./flight-view.component.css']
})
export class FlightViewComponent implements OnInit {
  admin: AirlineCompanyAdmin;
  passenger: Passenger;
  flights: Flight[] = [];
  read = false;
  errorMessage = '';
  username = '';

  constructor(private service: FlightService, private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) { }
  ngOnInit() {
    // this.whosInCharge();
    this.loadAll();
  }
  loadAll() {
    this.flights = [];
    this.whosInCharge();
    // if (this.admin !== undefined) {
    //   // this.flights = this.admin.airlineCompany.flights;
    //   this.read = true;
    // } else {
    //   this.service.get().subscribe(
    //     data => {
    //       this.flights = data;
    //       this.read = true;
    //     }, error => {
    //       this.errorMessage = error.errorMessage;
    //       alert(this.errorMessage);
    //     }
    //   );
    // }
  }
  whosInCharge() {
    this.username = this.tokenStorage.getUsername();
    if (this.tokenStorage.getAuthorities().includes('ROLE_AIRLINECOMPANYADMIN')) {
      console.log('admin');
      this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
        data => {
          this.admin = data;
          this.setAdmin(data);
        }
      );
    } else if (this.tokenStorage.getAuthorities().includes('ROLE_PASSENGER')) {
      console.log('passenger');
      this.profileService.getPassengerByUsername(this.username).subscribe(
        data => {
          this.passenger = data;
          this.setPassenger(data);
        }
      );
    } else {
      this.setVisitor();
      alert('You are not signed in. You can only browse flights.');
    }
  }
  setEmpty() {
    for (const flight of this.flights) {
      if (flight.transfers[0] === undefined) {
        flight.transfers[0] = new PlaceAndTime();
      }
      if (flight.transfers[1] === undefined) {
        flight.transfers[1] = new PlaceAndTime();
      }
    }
  }
  setAdmin(admin: AirlineCompanyAdmin) {
    this.admin = admin;
    // this.flights = admin.airlineCompany.flights;
    this.flights = [];
    for (let i = 0; i < admin.airlineCompany.flights.length; i++) {
      if (admin.airlineCompany.flights[i].deleted === false) {
        if (!this.flights.includes(admin.airlineCompany.flights[i])) {
          this.flights.push(admin.airlineCompany.flights[i]);
        }
      }
    }
    console.log(this.flights);
    this.read = true;
    this.setEmpty();
  }
  setPassenger(passenger: Passenger) {
    this.passenger = passenger;
    this.service.get().subscribe(
      data => {
        for (let i = 0; i < data.length; i++) {
          if (data[i].deleted === false) {
            this.flights.push(data[i]);
          }
        }
        this.read = true;
      }, error => {
        this.errorMessage = error.errorMessage;
        alert(this.errorMessage);
      }
    );
    this.setEmpty();
  }
  setVisitor() {
    this.service.get().subscribe(
      data => {
        for (let i = 0; i < data.length; i++) {
          if (data[i].deleted === false) {
            this.flights.push(data[i]);
            console.log(data[i]);
          }
        }
        this.read = true;
      }, error => {
        this.errorMessage = error.errorMessage;
        alert(this.errorMessage);
      }
    );
    this.setEmpty();
  }
  edit(flight: Flight) {
    if (this.admin.airlineCompany !== undefined && this.admin.airlineCompany.flights.includes(flight)) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
        id: 1,
        flight
      };
      const dialogRef = this.dialog.open(EditFlightComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        result => {
          console.log('Dialog closed.');
          console.log(result);
          this.loadAll();
        }
      );
    } else {
      console.log('The flight does not belong to your company.');
    }
  }
  delete(flight: Flight) {
    if (this.admin.airlineCompany !== undefined && this.admin.airlineCompany.flights.includes(flight)) {
      this.service.delete(flight.id).subscribe(
        data => {
          if (data === true) {
            alert('Flight was deleted.');
            this.loadAll();
            // location.reload();
            this.router.navigateByUrl('flight/view');
          } else {
            alert('Deletion was unsuccessful.');
          }
        }
      );
    }
  }
  discount(flight: Flight) {
    if (this.admin.airlineCompany !== undefined && this.admin.airlineCompany.flights.includes(flight)) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
        id: 1,
        flight
      };
      const dialogRef = this.dialog.open(DiscountTicketsComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        result => {
          console.log('Dialog closed.');
          console.log(result);
          this.loadAll();
        }
      );
    } else {
      console.log('The flight does not belong to your company.');
    }
  }
  reserveTickets(flight: Flight) {
    if (this.passenger !== undefined) {
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.data = {
        id: 1,
        flight
      };
      const dialogRef = this.dialog.open(TicketsViewComponent, dialogConfig);
      dialogRef.afterClosed().subscribe(
        result => {
          console.log('Dialog closed.');
          console.log(result);
          this.loadAll();
        }
      );
    } else {
      console.log('The flight does not belong to your company.');
    }
  }

}
