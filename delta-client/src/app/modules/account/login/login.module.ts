import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material';

@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule, FormsModule, MatDialogModule
  ],
  providers: [
    {
      provide: MatDialogRef, useValue: {}
    },
    {
      provide: MAT_DIALOG_DATA, useValue: []
    }
  ],
  exports: [LoginComponent]
})
export class LoginModule { }
