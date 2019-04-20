import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from './modules/account/register/register/register.component';
import {ValidateComponent} from './modules/account/register/validate/validate.component';
import {LoginComponent} from './modules/account/login/login/login.component';

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
    path: 'signin',
    component: LoginComponent
  },
  // {
  //   path: 'validate/token',
  //   component: ValidateComponent
  // },
  {
    path: 'validate/token/:tkn',
    component: ValidateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
