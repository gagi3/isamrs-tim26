import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AirlineCompanyRegistrationComponent } from './airline-company-registration/airline-company-registration.component';
import {FormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';

@NgModule({
  declarations: [AirlineCompanyRegistrationComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule]
})
export class AirlineCompanyRegistrationModule { }
