import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AirlineCompanyRegistrationModule} from './airline-company-registration/airline-company-registration.module';
import {RoleGuardService} from '../shared/role-guard.service';
import {httpInterceptorProviders} from '../shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AirlineCompanyRegistrationModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule]
})
export class AdministrationModule {
}
