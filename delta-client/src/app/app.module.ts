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
    ModerationModule,
    FormsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
