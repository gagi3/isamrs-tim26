import {Component, OnInit, ViewChild} from '@angular/core';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';

@Component({
  selector: 'app-edit-company',
  templateUrl: './edit-company.component.html',
  styleUrls: ['./edit-company.component.css']
})
export class EditCompanyComponent implements OnInit {
  @ViewChild('header') header: HeaderComponent;
  showView = 'edit-company';

  constructor() {
  }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
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
