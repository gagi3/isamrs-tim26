import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from './modules/account/register/register/register.component';
import {ValidateComponent} from './modules/account/register/validate/validate.component';
import {LoginComponent} from './modules/account/login/login/login.component';
import {PassengerProfileComponent} from './modules/account/profile/passenger-profile/passenger-profile.component';
import {AirlineCompanyAdminProfileComponent} from './modules/account/profile/airline-company-admin-profile/airline-company-admin-profile.component';
import {SystemAdminProfileComponent} from './modules/account/profile/system-admin-profile/system-admin-profile.component';
import {AirlineCompanyRegistrationComponent} from './modules/administration/airline-company-registration/airline-company-registration/airline-company-registration.component';
import {AddAirplaneComponent} from './modules/moderation/airplane/add-airplane/add-airplane.component';
import {AddPriceListComponent} from './modules/moderation/price-list/add-price-list/add-price-list.component';
import {EditPriceListComponent} from './modules/moderation/price-list/edit-price-list/edit-price-list.component';

const routes: Routes = [
  // {
  //   path: 'signup',
  //   component: RegisterComponent
  // },
  {
    path: 'airline-company/add',
    component: AirlineCompanyRegistrationComponent
  },
  {
    path: 'price-list/add',
    component: AddPriceListComponent
  },
  {
    path: 'price-list/edit',
    component: EditPriceListComponent
  },
  {
    path: 'airplane/add',
    component: AddAirplaneComponent
  },
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
  },
  {
    path: 'profile/passenger',
    component: PassengerProfileComponent
  },
  {
    path: 'profile/airline-company-admin',
    component: AirlineCompanyAdminProfileComponent
  },
  {
    path: 'profile/system-admin',
    component: SystemAdminProfileComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
