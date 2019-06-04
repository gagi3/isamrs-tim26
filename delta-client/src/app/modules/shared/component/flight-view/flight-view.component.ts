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

@Component({
  selector: 'app-flight-view',
  templateUrl: './flight-view.component.html',
  styleUrls: ['./flight-view.component.css']
})
export class FlightViewComponent implements OnInit {
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  passenger: Passenger = new Passenger();
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
    this.whosInCharge();
    if (this.admin.airlineCompany !== undefined) {
      // this.flights = this.admin.airlineCompany.flights;
      this.read = true;
    } else {
      this.service.get().subscribe(
        data => {
          this.flights = data;
          this.read = true;
        }, error => {
          this.errorMessage = error.errorMessage;
          alert(this.errorMessage);
        }
      );
    }
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
      alert('You are not signed in. You can only browse flights.');
    }
  }
  setAdmin(admin: AirlineCompanyAdmin) {
    this.admin = admin;
    this.flights = admin.airlineCompany.flights;
  }
  setPassenger(passenger: Passenger) {
    this.passenger = passenger;
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
            this.router.navigateByUrl('flight/view');
          } else {
            alert('Deletion was unsuccessful.');
          }
        }
      );
    }
  }
  reserveTickets(flight: Flight) {
    // TODO: Implement passenger ticket reservation
  }

}
