import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditCompanyComponent} from './edit-company/edit-company.component';
import {HeaderModule} from '../../shared/modules/header/header.module';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [EditCompanyComponent],
  exports: [
    EditCompanyComponent
  ],
  imports: [
    CommonModule,
    HeaderModule,
    FormsModule
  ]
})
export class CompanyModule {
}
