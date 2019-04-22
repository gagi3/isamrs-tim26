import { Component, OnInit } from '@angular/core';
import {ProfileService} from '../shared/service/profile.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {Passenger} from '../shared/model/passenger';
import {AirlineCompanyAdmin} from '../shared/model/airline-company-admin';
import {SystemAdmin} from '../shared/model/system-admin';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  passenger: Passenger = new Passenger();
  passengerFound = false;
  airlineCompanyAdmin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  airlineCompanyAdminFound = false;
  systemAdmin: SystemAdmin = new SystemAdmin();
  systemAdminFound = false;
  passRepeat: string;
  show = 'profile';

  constructor(private service: ProfileService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    this.look();
  }
  look() {
    this.findPassenger();
    if (this.passengerFound === false) {
      this.findAirlineCompanyAdmin();
      if (this.airlineCompanyAdminFound === false) {
        this.findSystemAdmin();
        if (this.systemAdminFound === false) {
          alert('Nobody found with this username. You cheat!');
        }
      }
    }
  }
  findPassenger() {
    const username = this.tokenStorage.getUsername();
    this.service.getPassengerByUsername(username).subscribe(
      data => {
        this.passenger = data;
        this.passenger.id = data.id;
        if (typeof this.passenger !== 'undefined') {
          this.passengerFound = true;
        }
      }
    );
  }
  findAirlineCompanyAdmin() {
    const username = this.tokenStorage.getUsername();
    this.service.getAirlineCompanyAdminByUsername(username).subscribe(
      data => {
        this.airlineCompanyAdmin = data;
        this.airlineCompanyAdmin.id = data.id;
        if (typeof this.airlineCompanyAdmin !== 'undefined') {
          this.airlineCompanyAdminFound = true;
        }
      }
    );
  }
  findSystemAdmin() {
    const username = this.tokenStorage.getUsername();
    this.service.getSystemAdminByUsername(username).subscribe(
      data => {
        this.systemAdmin = data;
        this.systemAdmin.id = data.id;
        if (typeof this.systemAdmin !== 'undefined') {
          this.systemAdminFound = true;
        }
      }
    );
  }

}
