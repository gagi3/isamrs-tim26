import {Component, Input, OnInit, ViewChild} from '@angular/core';
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
import {DiscountTicketsComponent} from '../../../moderation/ticket/discount-tickets/discount-tickets.component';
import {TicketsViewComponent} from '../../../consumption/reservation/tickets-view/tickets-view.component';
import {HeaderComponent} from '../../modules/header/header/header.component';
import {AirlineCompany} from '../../model/airline-company';
import {AirlineCompanyService} from '../../airline-company.service';
import {DateTimeFormatPipe} from '../../date-time-format.pipe';

@Component({
  selector: 'app-flight-view',
  templateUrl: './flight-view.component.html',
  styleUrls: ['./flight-view.component.css']
})
export class FlightViewComponent implements OnInit {
  admin: AirlineCompanyAdmin;
  passenger: Passenger;
  flights: Flight[] = [];
  companies: AirlineCompany[] = [];
  destinations: string[] = [];
  read = false;
  errorMessage = '';
  username = '';
  @Input() flightSearch: any;
  @ViewChild('header') header: HeaderComponent;
  showView = 'flights';
  arr = '';
  dep = '';
  arrDate: Date = new Date();
  depDate: Date = new Date();
  distance = 0;
  priceFrom = 0;
  priceTo = 0;

  constructor(private service: FlightService, private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog, private companyService: AirlineCompanyService,
              private datePipe: DateTimeFormatPipe) {
  }

  ngOnInit() {
    // this.whosInCharge();
    this.loadAll();
  }

  loadAll() {
    this.flights = [];
    this.whosInCharge();
    if (this.admin !== undefined) {
      this.companies.push(this.admin.airlineCompany);
    } else {
      this.companyService.getAll().subscribe(
        data => {
          this.companies = data;
          this.mapCompanies(data);
        }
      );
    }
  }

  mapCompanies(data: AirlineCompany[]) {
    const fts = [];
    for (const c of data) {
      for (const f of this.flights) {
        for (const ff of c.flights) {
          if (ff.id === f.id) {
            f.airlineCompany = c;
            fts.push(f);
          }
        }
      }
      for (const d of c.destinations) {
        if (!this.destinations.includes(d)) {
          this.destinations.push(d);
        }
      }
    }
    this.flights = fts;
  }

  whosInCharge() {
    this.username = this.tokenStorage.getUsername();
    if (this.tokenStorage.getAuthorities().includes('ROLE_AIRLINECOMPANYADMIN')) {
      console.log('admin');
      this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
        data => {
          this.admin = data;
          this.setAdmin(data);
          this.header.airlineCompanyAdminView();
        }
      );
    } else if (this.tokenStorage.getAuthorities().includes('ROLE_PASSENGER')) {
      console.log('passenger');
      this.profileService.getPassengerByUsername(this.username).subscribe(
        data => {
          this.passenger = data;
          this.setPassenger(data);
          this.header.passengerView();
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
    this.header.airlineCompanyAdminView();
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
    this.header.passengerView();
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
          this.flightSearch.reset();
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
            this.flightSearch.reset();
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
          this.flightSearch.reset();
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
          this.flightSearch.reset();
        }
      );
    } else {
      console.log('The flight does not belong to your company.');
    }
  }

  loadSearchFilter(filtered: Flight[]) {
    this.flights = filtered;
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

  changeDep() {
    this.filterDeparture(this.dep);
  }

  changeArr() {
    this.filterArrival(this.arr);
  }

  changeDepDate() {
    this.filterDepDate(this.depDate);
  }

  changeArrTime() {
    this.filterArrDate(this.arrDate);
  }

  filterDeparture(city: string) {
    console.log('filtering');
    const flights = [];
    for (const f of this.flights) {
      if (f.departure.thePlace === city) {
        flights.push(f);
      }
    }
    this.flights = flights;
  }

  filterDepDate(date: Date) {
    date = new Date(date);
    const flights = [];
    for (const f of this.flights) {
      console.log(f.departure.theTime);
      console.log(f.departure);
      let dd = this.datePipe.transform(f.departure.theTime);
      console.log(dd);
      dd = new Date(f.departure.theTime);
      console.log(dd);
      console.log(date);
      console.log(dd.getFullYear());
      if (dd.getFullYear() === date.getFullYear() &&
        dd.getMonth() === date.getMonth() &&
        dd.getDate() === date.getDate()) {
        flights.push(f);
      }
    }
    this.flights = flights;
  }

  filterArrival(city: string) {
    const flights = [];
    for (const f of this.flights) {
      if (f.arrival.thePlace === city) {
        flights.push(f);
      }
    }
    this.flights = flights;
  }

  filterArrDate(date: Date) {
    date = new Date(date);
    const flights = [];
    for (const f of this.flights) {
      console.log(f.arrival.theTime);
      console.log(f.arrival);
      const dd = new Date(f.arrival.theTime);
      console.log(dd);
      console.log(date);
      console.log(dd.getFullYear());
      if (dd.getFullYear() === date.getFullYear() &&
        dd.getMonth() === date.getMonth() &&
        dd.getDate() === date.getDate()) {
        flights.push(f);
      }
    }
    this.flights = flights;
  }

  changePriceFrom() {
    this.filterPriceFrom(this.priceFrom);
  }

  filterPriceFrom(price: number) {
    const flights = [];
    for (const f of this.flights) {
      for (const t of f.tickets) {
        if (t.price >= price && !flights.includes(f)) {
          flights.push(f);
        }
      }
    }
    this.flights = flights;
  }

  changePriceTo() {
    this.filterPriceTo(this.priceTo);
  }

  filterPriceTo(price: number) {
    const flights = [];
    for (const f of this.flights) {
      for (const t of f.tickets) {
        if (t.price <= price && !flights.includes(f)) {
          flights.push(f);
        }
      }
    }
    this.flights = flights;
  }

  changeDistance() {
    this.filterDistance(this.distance);
  }

  filterDistance(distance: number) {
    const flights = [];
    for (const f of this.flights) {
      if (distance === f.distance && !flights.includes(f)) {
        flights.push(f);
      }
    }
    this.flights = flights;
  }

  reset() {
    this.flights = [];
    this.whosInCharge();
  }
}
