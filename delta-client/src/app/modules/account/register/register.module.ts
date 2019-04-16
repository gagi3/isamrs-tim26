import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import { ValidateComponent } from './validate/validate.component';

@NgModule({
  declarations: [RegisterComponent, ValidateComponent],
  imports: [
    CommonModule
  ],
})
export class RegisterModule { }
