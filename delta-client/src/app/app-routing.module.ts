import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegisterComponent} from './modules/account/register/register/register.component';
import {ValidateComponent} from './modules/account/register/validate/validate.component';

const routes: Routes = [
  // {
  //   path: 'signup',
  //   component: RegisterComponent
  // },
  {
    path: 'signup/passenger',
    component: RegisterComponent
  },
  {
    path: 'signup/system-admin',
    component: RegisterComponent
  },
  {
    path: 'signup/airline-company-admin',
    component: RegisterComponent
  },
  {
    path: 'validate/token=',
    component: ValidateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
