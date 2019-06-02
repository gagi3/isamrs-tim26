import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddFlightComponent} from './add-flight/add-flight.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {DateTimeFormatPipe} from '../../shared/date-time-format.pipe';

@NgModule({
  declarations: [AddFlightComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule, DateTimeFormatPipe]
})
export class FlightModule { }
