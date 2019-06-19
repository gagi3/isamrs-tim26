import {Component, OnInit, ViewChild} from '@angular/core';
import {FlightDTO} from '../flight-dto';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Router} from '@angular/router';
import {FlightService} from '../flight.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {Airplane} from '../../../shared/model/airplane';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {DateTimeFormatPipe} from '../../../shared/date-time-format.pipe';
import {PlaceAndTimeDTO} from '../place-and-time-dto';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.css']
})
export class AddFlightComponent implements OnInit {
  airplanes: Airplane[];
  airplane: Airplane = new Airplane();
  selectedAirplane: Airplane = new Airplane();
  destinations: string[];
  DTO: FlightDTO = new FlightDTO();
  allAirplanes: [] = [];
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  added = false;
  failed = false;
  errorMessage = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'add-flight';

  constructor(private router: Router, private service: FlightService, private tokenStorage: TokenStorageService,
              private adminService: ProfileService, private datePipe: DateTimeFormatPipe) { }
  cancel() {
    this.router.navigateByUrl('');
  }
  plusOne() {
    console.log(this.DTO.generateTickets);
    if (this.DTO.transfers.length < 2) {
      this.DTO.transfers.push(new PlaceAndTimeDTO());
    } else {
      alert('Can\'t have more than two transfer points.');
    }
  }
  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.DTO.transfers = [];
    this.DTO.generateTickets = false;
    this.username = this.tokenStorage.getUsername();
    this.adminService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
        this.getAirplanes(data);
        this.getDestinations(data);
      }
    );
  }
  getDestinations(admin: AirlineCompanyAdmin) {
    this.destinations = admin.airlineCompany.destinations;
  }
  getAirplanes(admin: AirlineCompanyAdmin) {
    this.airplanes = admin.airlineCompany.airplanes;
  }
  onSubmit() {
    const check = this.check();
    this.DTO.arrival.theTime = this.datePipe.transform(this.DTO.arrival.theTime);
    this.DTO.departure.theTime = this.datePipe.transform(this.DTO.departure.theTime);
    for (let i = 0; i < this.DTO.transfers.length; i++) {
      this.DTO.transfers[i].theTime = this.datePipe.transform(this.DTO.transfers[i].theTime);
    }
    if (check === false) {
      this.service.create(this.DTO).subscribe(
        data => {
          this.added = true;
          window.alert('Registration was successful!');
          this.router.navigateByUrl('');
        }, error => {
          this.errorMessage = error.errorMessage;
          this.added = false;
          this.failed = true;
          window.alert(this.errorMessage);
        }
      );
    } else {
      alert('There was en error.');
    }
  }
  check(): boolean {
    if (this.DTO.arrival.thePlace === this.DTO.departure.thePlace) {
      alert('Arrival and departure can not be the same.');
      return true;
    }
    if (this.DTO.transfers.length === 2) {
      if (this.DTO.transfers[1].thePlace === this.DTO.transfers[0].thePlace) {
        alert('Transfer destinations can not be the same.');
        return true;
      }
      if (this.DTO.transfers[1].thePlace === this.DTO.arrival.thePlace) {
        alert('Transfer point 2 can not be the same as the arrival destination.');
        return true;
      }
      if (this.DTO.transfers[1].thePlace === this.DTO.departure.thePlace) {
        alert('Transfer point 2 can not be the same as the departure destination.');
        return true;
      }
    }
    if (this.DTO.transfers.length > 0) {
      if (this.DTO.transfers[0].thePlace === this.DTO.arrival.thePlace) {
        alert('Transfer point 1 can not be the same as the arrival destination.');
        return true;
      }
      if (this.DTO.transfers[0].thePlace === this.DTO.departure.thePlace) {
        alert('Transfer point 1 can not be the same as the departure destination.');
        return true;
      }
    }
    return false;
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
