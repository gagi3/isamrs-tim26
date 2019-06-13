import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AccountModule} from './modules/account/account.module';
import {AdministrationModule} from './modules/administration/administration.module';
import {ConsumptionModule} from './modules/consumption/consumption.module';
import {ModerationModule} from './modules/moderation/moderation.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {RoleGuardService} from './modules/shared/role-guard.service';
import {httpInterceptorProviders} from './modules/shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';
import {DateTimeFormatPipe} from './modules/shared/date-time-format.pipe';
import { FlightViewComponent } from './modules/shared/component/flight-view/flight-view.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { TicketViewComponent } from './modules/shared/component/ticket-view/ticket-view.component';
import { FlightSearchComponent } from './modules/shared/component/flight-search/flight-search.component';

@NgModule({
  declarations: [
    AppComponent,
    DateTimeFormatPipe,
    FlightViewComponent,
    TicketViewComponent,
    FlightSearchComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    AccountModule,
    AdministrationModule,
    ConsumptionModule,
    ModerationModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
