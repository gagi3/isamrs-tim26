import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketsViewComponent } from './tickets-view/tickets-view.component';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {DateTimeFormatPipe} from '../../shared/date-time-format.pipe';
import { DiscountedTicketsViewComponent } from './discounted-tickets-view/discounted-tickets-view.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { TicketReservationComponent } from './ticket-reservation/ticket-reservation.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { ViewTicketsComponent } from './view-tickets/view-tickets.component';

@NgModule({
  declarations: [TicketsViewComponent, DiscountedTicketsViewComponent, TicketReservationComponent, ConfirmationComponent, ViewTicketsComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    BrowserAnimationsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule, DateTimeFormatPipe],
  exports: [TicketReservationComponent],
  entryComponents: [TicketReservationComponent]
})
export class ReservationModule { }
