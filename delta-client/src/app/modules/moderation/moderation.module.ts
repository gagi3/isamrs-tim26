import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AirplaneModule} from './airplane/airplane.module';
import {PriceListModule} from './price-list/price-list.module';
import {FlightModule} from './flight/flight.module';
import {TicketModule} from './ticket/ticket.module';
import {ReservationModule} from './reservation/reservation.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AirplaneModule,
    PriceListModule,
    FlightModule,
    TicketModule,
    ReservationModule
  ]
})
export class ModerationModule { }
