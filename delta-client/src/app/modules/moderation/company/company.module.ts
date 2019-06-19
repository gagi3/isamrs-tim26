import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditCompanyComponent } from './edit-company/edit-company.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HeaderModule} from "../../shared/modules/header/header.module";

@NgModule({
  declarations: [EditCompanyComponent],
  exports: [
    EditCompanyComponent
  ],
  imports: [
    CommonModule,
    HeaderModule
  ]
})
export class CompanyModule { }
