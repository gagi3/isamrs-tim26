import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AddAirplaneComponent} from './add-airplane/add-airplane.component';
import {MatDialogModule} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {TicketModule} from '../ticket/ticket.module';
import {ReservationModule} from '../reservation/reservation.module';
import {PriceListModule} from '../price-list/price-list.module';
import {FlightModule} from '../flight/flight.module';
import {CompanyModule} from '../company/company.module';
import {HeaderModule} from '../../shared/modules/header/header.module';

@NgModule({
  declarations: [AddAirplaneComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule,
    TicketModule,
    ReservationModule,
    PriceListModule,
    FlightModule,
    CompanyModule,
    HeaderModule
  ],
  exports: [AddAirplaneComponent]
})
export class AirplaneModule { }
