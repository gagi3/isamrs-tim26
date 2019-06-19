import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Airplane} from '../../../shared/model/airplane';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Router} from '@angular/router';
import {FlightService} from '../flight.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {DateTimeFormatPipe} from '../../../shared/date-time-format.pipe';
import {Flight} from '../../../shared/model/flight';
import {PlaceAndTime} from '../../../shared/model/place-and-time';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';

@Component({
  selector: 'app-edit-flight',
  templateUrl: './edit-flight.component.html',
  styleUrls: ['./edit-flight.component.css']
})
export class EditFlightComponent implements OnInit {
  airplanes: Airplane[];
  airplane: Airplane = new Airplane();
  selectedAirplane: Airplane;
  destinations: string[];
  DTO: Flight = new Flight();
  allAirplanes: [] = [];
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  added = false;
  failed = false;
  errorMessage = '';
  show = 'flight';
  @ViewChild('header') header: HeaderComponent;
  showView = 'edit-flight';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: FlightService, private tokenStorage: TokenStorageService,
              private adminService: ProfileService, private datePipe: DateTimeFormatPipe) {
  }

  edit() {
  }

  cancel() {
    this.dialogRef.close();
  }

  plusOne() {
    if (this.DTO.transfers.length < 2) {
      this.DTO.transfers.push(new PlaceAndTime());
    } else {
      alert('Can\'t have more than two transfer points.');
    }
  }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.dialogRef.updateSize('40%', '80%');
    this.username = this.tokenStorage.getUsername();
    this.adminService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
        this.getAirplanes(data);
        this.getDestinations(data);
      }
    );
    this.DTO = this.data.flight;
  }

  getDestinations(admin: AirlineCompanyAdmin) {
    this.destinations = admin.airlineCompany.destinations;
  }

  getAirplanes(admin: AirlineCompanyAdmin) {
    this.airplanes = admin.airlineCompany.airplanes;
  }

  deserializeArrival() {
    this.DTO.arrival.theTime = this.datePipe.transform(this.DTO.arrival.theTime);
  }

  deserializeDeparture() {
    this.DTO.departure.theTime = this.datePipe.transform(this.DTO.departure.theTime);
  }

  deserializeTP1() {
    this.DTO.transfers[0].theTime = this.datePipe.transform(this.DTO.transfers[0].theTime);
  }

  deserializeTP2() {
    this.DTO.transfers[1].theTime = this.datePipe.transform(this.DTO.transfers[1].theTime);
  }

  onSubmit() {
    const check = this.check();
    console.log(this.DTO);
    // this.DTO.arrival.theTime = this.datePipe.transform(this.DTO.arrival.theTime);
    // this.DTO.departure.theTime = this.datePipe.transform(this.DTO.departure.theTime);
    // // @ts-ignore
    // for (let i = 0; i < this.DTO.transfers.length; i++) {
    //   this.DTO.transfers[i].theTime = this.datePipe.transform(this.DTO.transfers[i].theTime);
    // }
    if (check === false) {
      this.service.update(this.DTO).subscribe(
        data => {
          this.added = true;
          window.alert('Update was successful!');
          this.router.navigateByUrl('');
          this.dialogRef.close();
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
