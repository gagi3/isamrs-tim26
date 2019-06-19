import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Passenger} from '../shared/model/passenger';
import {ProfileService} from '../shared/service/profile.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-passenger-profile',
  templateUrl: './passenger-profile.component.html',
  styleUrls: ['./passenger-profile.component.css']
})
export class PassengerProfileComponent implements OnInit {

  @ViewChild('inputEl1') public inputEl1: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;
  @ViewChild('inputEl4') public inputEl4: ElementRef;
  @ViewChild('inputEl5') public inputEl5: ElementRef;
  @ViewChild('repeat') public repeat: ElementRef;
  passenger: Passenger = new Passenger();
  passengerFound = false;
  passwordRepeat: string;
  show = 'profile';
  @ViewChild('header') header: HeaderComponent;
  showView = 'profile-view';

  constructor(private service: ProfileService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit() {
    this.look();
  }
  look() {
    this.findPassenger();
    if (this.passengerFound === false) {
      alert('Nobody found with this username. You cheat!');
    }
  }
  findPassenger() {
    const username = this.tokenStorage.getUsername();
    this.service.getPassengerByUsername(username).subscribe(
      data => {
        this.passenger = data;
        this.passenger.id = data.id;
        this.header.passengerView();
        if (typeof this.passenger !== 'undefined') {
          this.passengerFound = true;
        }
      }
    );
  }
  clickChange() {
    this.show = 'change';
    this.inputEl1.nativeElement.disabled = false;
    this.inputEl2.nativeElement.disabled = false;
    this.inputEl3.nativeElement.disabled = false;
    this.inputEl4.nativeElement.disabled = false;
    this.inputEl5.nativeElement.disabled = false;
  }
  clickCancel() {
    this.show = 'profile';
    this.inputEl1.nativeElement.disabled = true;
    this.inputEl2.nativeElement.disabled = true;
    this.inputEl3.nativeElement.disabled = true;
    this.inputEl4.nativeElement.disabled = true;
    this.inputEl5.nativeElement.disabled = true;
  }
  clickSave() {
    if (this.passenger.password === this.passwordRepeat) {
      this.service.updatePassenger(this.passenger).subscribe(
        data => {
          this.passenger = data;
          alert('Profile info changed!');
          this.clickCancel();
        }
      );
    } else {
      alert('Passwords do not match!');
    }
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
