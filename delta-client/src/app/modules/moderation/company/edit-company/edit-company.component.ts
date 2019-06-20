import {Component, OnInit, ViewChild} from '@angular/core';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';
import {AirlineCompany} from '../../../shared/model/airline-company';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {AirlineCompanyService} from '../../../shared/airline-company.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {Router} from '@angular/router';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';

@Component({
  selector: 'app-edit-company',
  templateUrl: './edit-company.component.html',
  styleUrls: ['./edit-company.component.css']
})
export class EditCompanyComponent implements OnInit {
  company: AirlineCompany = new AirlineCompany();
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  username = '';
  isUpdated = false;
  failed = false;
  @ViewChild('header') header: HeaderComponent;
  showView = 'edit-company';

  constructor(private service: AirlineCompanyService, private tokenStorage: TokenStorageService, private router: Router,
              private profileService: ProfileService) {
  }

  cancel() {
    this.router.navigateByUrl('airline-company/edit');
  }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.username = this.tokenStorage.getUsername();
    this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
        this.company = data.airlineCompany;
      }
    );
  }

  onSubmit() {
    this.service.update(this.company).subscribe(
      data => {
        this.failed = false;
        alert('Update successful!');
        this.router.navigateByUrl('airline-company/edit');
        this.isUpdated = true;
      }, error => {
        this.isUpdated = false;
        this.failed = true;
        alert('Update not successful!');
        this.router.navigateByUrl('airline-company/edit');
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
