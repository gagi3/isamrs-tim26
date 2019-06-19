import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AirlineCompanyAdmin} from '../shared/model/airline-company-admin';
import {ProfileService} from '../shared/service/profile.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-airline-company-admin-profile',
  templateUrl: './airline-company-admin-profile.component.html',
  styleUrls: ['./airline-company-admin-profile.component.css']
})
export class AirlineCompanyAdminProfileComponent implements OnInit {
  @ViewChild('inputEl1') public inputEl1: ElementRef;
  @ViewChild('inputEl2') public inputEl2: ElementRef;
  @ViewChild('inputEl3') public inputEl3: ElementRef;
  @ViewChild('inputEl4') public inputEl4: ElementRef;
  @ViewChild('inputEl5') public inputEl5: ElementRef;
  @ViewChild('repeat') public repeat: ElementRef;
  airlineCompanyAdmin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  airlineCompanyAdminFound = false;
  passwordRepeat: string;
  show = 'profile';
  @ViewChild('header') header: HeaderComponent;
  showView = 'profile-view';

  constructor(private service: ProfileService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit() {
    this.look();
  }
  look() {
    this.findAirlineCompanyAdmin();
    if (this.airlineCompanyAdminFound === false) {
      alert('Nobody found with this username. You cheat!');
    }
  }
  findAirlineCompanyAdmin() {
    const username = this.tokenStorage.getUsername();
    this.service.getAirlineCompanyAdminByUsername(username).subscribe(
      data => {
        this.airlineCompanyAdmin = data;
        this.airlineCompanyAdmin.id = data.id;
        this.header.airlineCompanyAdminView();
        if (typeof this.airlineCompanyAdmin !== 'undefined') {
          this.airlineCompanyAdminFound = true;
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
    if (this.airlineCompanyAdmin.password === this.passwordRepeat) {
      this.service.updateAirlineCompanyAdmin(this.airlineCompanyAdmin).subscribe(
        data => {
          this.airlineCompanyAdmin = data;
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
