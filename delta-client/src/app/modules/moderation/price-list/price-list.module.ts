import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPriceListComponent } from './add-price-list/add-price-list.component';
import {FormsModule} from '@angular/forms';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';

@NgModule({
  declarations: [AddPriceListComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule]
})
export class PriceListModule { }
