import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DiscountTicketsComponent } from './discount-tickets/discount-tickets.component';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {DateTimeFormatPipe} from '../../shared/date-time-format.pipe';

@NgModule({
  declarations: [DiscountTicketsComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule, DateTimeFormatPipe]
})
export class TicketModule { }
