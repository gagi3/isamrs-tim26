import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RegisterComponent} from './register/register.component';
import {ValidateComponent} from './validate/validate.component';
import {FormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [RegisterComponent, ValidateComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
})
export class RegisterModule { }
