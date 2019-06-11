import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BusinessReportViewComponent } from './business-report-view/business-report-view.component';
import { BusinessReportSelectComponent } from './business-report-select/business-report-select.component';
import {MatDialogModule} from '@angular/material';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [BusinessReportViewComponent, BusinessReportSelectComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule
  ]
})
export class ReservationModule { }
