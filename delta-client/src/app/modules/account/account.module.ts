import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterModule} from './register/register.module';
import {LoginModule} from './login/login.module';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RegisterModule, LoginModule, FormsModule
  ],
  exports: []
})
export class AccountModule { }
