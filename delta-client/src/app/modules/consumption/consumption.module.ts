import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReservationModule} from './reservation/reservation.module';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {InteractionModule} from './interaction/interaction.module';

@NgModule({
  declarations: [],
  imports: [
    ReservationModule,
    InteractionModule,
    CommonModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    BrowserAnimationsModule
  ]
})
export class ConsumptionModule { }
