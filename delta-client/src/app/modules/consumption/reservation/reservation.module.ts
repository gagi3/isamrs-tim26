import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketsViewComponent } from './tickets-view/tickets-view.component';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {DateTimeFormatPipe} from '../../shared/date-time-format.pipe';
import { DiscountedTicketsViewComponent } from './discounted-tickets-view/discounted-tickets-view.component';

@NgModule({
  declarations: [TicketsViewComponent, DiscountedTicketsViewComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule, DateTimeFormatPipe]
})
export class ReservationModule { }
