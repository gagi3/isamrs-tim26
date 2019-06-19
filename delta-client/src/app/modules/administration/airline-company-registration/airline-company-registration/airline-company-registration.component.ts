import {Component, OnInit, ViewChild} from '@angular/core';
import {AirlineCompanyRegistrationDTO} from '../airline-company-registration-dto';
import {Router} from '@angular/router';
import {AirlineCompanyRegistrationService} from '../airline-company-registration.service';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';

@Component({
  selector: 'app-airline-company-registration',
  templateUrl: './airline-company-registration.component.html',
  styleUrls: ['./airline-company-registration.component.css']
})
export class AirlineCompanyRegistrationComponent implements OnInit {
  registrationDTO: AirlineCompanyRegistrationDTO = new AirlineCompanyRegistrationDTO();
  isRegistered = false;
  failed = false;
  errorMessage = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'add-company';

  constructor(private router: Router, private registerService: AirlineCompanyRegistrationService) {
  }

  ngOnInit() {
    this.header.systemAdminView();
  }

  cancel() {
    this.router.navigateByUrl('');
  }

  onSubmit() {
    this.registerService.register(this.registrationDTO).subscribe(
      data => {
        window.alert('Registration was successful!');
        this.isRegistered = true;
        this.failed = false;
        this.router.navigateByUrl('');
      }, error => {
        console.log(error);
        this.errorMessage = error.errorMessage;
        this.isRegistered = false;
        this.failed = true;
        window.alert(this.errorMessage);
      }
    );
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
