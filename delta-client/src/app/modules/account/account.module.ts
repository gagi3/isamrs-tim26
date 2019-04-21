import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterModule} from './register/register.module';
import {LoginModule} from './login/login.module';
import {FormsModule} from '@angular/forms';
import {ProfileModule} from './profile/profile.module';
import {RoleGuardService} from '../shared/role-guard.service';
import {httpInterceptorProviders} from '../shared/auth-interceptor';
import {MatDialogModule} from '@angular/material';

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RegisterModule, LoginModule, FormsModule, ProfileModule
  ],
  exports: [],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule]
})
export class AccountModule { }
