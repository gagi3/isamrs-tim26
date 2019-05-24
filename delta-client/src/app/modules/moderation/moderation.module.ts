import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AirplaneModule} from './airplane/airplane.module';
import {PriceListModule} from './price-list/price-list.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AirplaneModule,
    PriceListModule
  ]
})
export class ModerationModule { }
