import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AccountModule} from './modules/account/account.module';
import {AdministrationModule} from './modules/administration/administration.module';
import {ConsumptionModule} from './modules/consumption/consumption.module';
import {ModerationModule} from './modules/moderation/moderation.module';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    AccountModule,
    AdministrationModule,
    ConsumptionModule,
    ModerationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
