import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProfileComponent} from './profile/profile.component';
import {AirlineCompanyAdminProfileComponent} from './airline-company-admin-profile/airline-company-admin-profile.component';
import {SystemAdminProfileComponent} from './system-admin-profile/system-admin-profile.component';
import {PassengerProfileComponent} from './passenger-profile/passenger-profile.component';
import {FormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';

@NgModule({
  declarations: [ProfileComponent, AirlineCompanyAdminProfileComponent, SystemAdminProfileComponent, PassengerProfileComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule]
})
export class ProfileModule { }
