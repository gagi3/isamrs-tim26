import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
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

@Component({
  selector: 'app-edit-flight',
  templateUrl: './edit-flight.component.html',
  styleUrls: ['./edit-flight.component.css']
})
export class EditFlightComponent implements OnInit {
  airplanes: Airplane[];
  airplane: Airplane = new Airplane();
  selectedAirplane: Airplane = new Airplane();
  destinations: string[];
  DTO: Flight = new Flight();
  allAirplanes: [] = [];
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  added = false;
  failed = false;
  errorMessage = '';
  show = 'flight';
  @ViewChild('inputEl1') public inputEl1: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;
  @ViewChild('inputEl4') public inputEl4: ElementRef;
  @ViewChild('inputEl5') public inputEl5: ElementRef;
  @ViewChild('inputEl6') public inputEl6: ElementRef;
  @ViewChild('inputEl7') public inputEl7: ElementRef;
  @ViewChild('inputEl8') public inputEl8: ElementRef;
  @ViewChild('inputEl9') public inputEl9: ElementRef;
  @ViewChild('inputEl10') public inputEl10: ElementRef;
  @ViewChild('inputEl11') public inputEl11: ElementRef;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: FlightService, private tokenStorage: TokenStorageService,
              private adminService: ProfileService, private datePipe: DateTimeFormatPipe) { }
  edit() {
    this.show = 'change';
    this.inputEl1.nativeElement.disabled = false;
    this.inputEl2.nativeElement.disabled = false;
    this.inputEl3.nativeElement.disabled = false;
    this.inputEl4.nativeElement.disabled = false;
    this.inputEl5.nativeElement.disabled = false;
    this.inputEl6.nativeElement.disabled = false;
    this.inputEl7.nativeElement.disabled = false;
    this.inputEl8.nativeElement.disabled = false;
    this.inputEl9.nativeElement.disabled = false;
    this.inputEl10.nativeElement.disabled = false;
    this.inputEl11.nativeElement.disabled = false;
  }
  cancel() {
    this.show = 'flight';
    this.inputEl1.nativeElement.disabled = true;
    this.inputEl2.nativeElement.disabled = true;
    this.inputEl3.nativeElement.disabled = true;
    this.inputEl4.nativeElement.disabled = true;
    this.inputEl5.nativeElement.disabled = true;
    this.inputEl6.nativeElement.disabled = true;
    this.inputEl7.nativeElement.disabled = true;
    this.inputEl8.nativeElement.disabled = true;
    this.inputEl9.nativeElement.disabled = true;
    this.inputEl10.nativeElement.disabled = true;
    this.inputEl11.nativeElement.disabled = true;
  }
  plusOne() {
    if (this.DTO.transfers.length < 2) {
      this.DTO.transfers.push(new PlaceAndTime());
    } else {
      alert('Can\'t have more than two transfer points.');
    }
  }
  ngOnInit() {
    this.dialogRef.updateSize('80%', '80%');
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
  onSubmit() {
    const check = this.check();
    this.DTO.arrival.theTime = this.datePipe.transform(this.DTO.arrival.theTime);
    this.DTO.departure.theTime = this.datePipe.transform(this.DTO.departure.theTime);
    // @ts-ignore
    for (let i = 0; i < this.DTO.transfers.length; i++) {
      this.DTO.transfers[i].theTime = this.datePipe.transform(this.DTO.transfers[i].theTime);
    }
    if (check === false) {
      this.service.update(this.DTO).subscribe(
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
}
