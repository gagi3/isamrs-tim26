import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAirplaneComponent } from './add-airplane/add-airplane.component';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [AddAirplaneComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule
  ],
  exports: [AddAirplaneComponent]
})
export class AirplaneModule { }
