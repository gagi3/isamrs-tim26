import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddFlightComponent} from './add-flight/add-flight.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {DateTimeFormatPipe} from '../../shared/date-time-format.pipe';
import { EditFlightComponent } from './edit-flight/edit-flight.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HeaderModule} from "../../shared/modules/header/header.module";

@NgModule({
  declarations: [AddFlightComponent, EditFlightComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    HeaderModule
  ],
  exports: [
    EditFlightComponent,
    AddFlightComponent
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule, DateTimeFormatPipe]
})
export class FlightModule { }
