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
import {AddFlightComponent} from './modules/moderation/flight/add-flight/add-flight.component';
import {EditFlightComponent} from './modules/moderation/flight/edit-flight/edit-flight.component';
import {FlightViewComponent} from './modules/shared/component/flight-view/flight-view.component';
import {DiscountTicketsComponent} from './modules/moderation/ticket/discount-tickets/discount-tickets.component';
import {DiscountedTicketsViewComponent} from './modules/consumption/reservation/discounted-tickets-view/discounted-tickets-view.component';
import {TicketsViewComponent} from './modules/consumption/reservation/tickets-view/tickets-view.component';
import {ConfirmationComponent} from './modules/consumption/reservation/confirmation/confirmation.component';
import {BusinessReportSelectComponent} from './modules/moderation/reservation/business-report-select/business-report-select.component';
import {BusinessReportViewComponent} from './modules/moderation/reservation/business-report-view/business-report-view.component';
import {ViewTicketsComponent} from './modules/consumption/reservation/view-tickets/view-tickets.component';
import {FriendsViewComponent} from './modules/consumption/interaction/friends-view/friends-view.component';
import {FindFriendsComponent} from './modules/consumption/interaction/find-friends/find-friends.component';
import {FriendshipRequestsComponent} from './modules/consumption/interaction/friendship-requests/friendship-requests.component';
import {EditCompanyComponent} from './modules/moderation/company/edit-company/edit-company.component';

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
    path: 'airline-company/edit',
    component: EditCompanyComponent
  },
  {
    path: 'flight/add',
    component: AddFlightComponent
  },
  {
    path: 'flight/update',
    component: EditFlightComponent
  },
  {
    path: 'flight/view',
    component: FlightViewComponent
  },
  {
    path: 'ticket/discount',
    component: DiscountTicketsComponent
  },
  {
    path: 'ticket/discounted',
    component: DiscountedTicketsViewComponent
  },
  {
    path: 'ticket/view',
    component: TicketsViewComponent
  },
  {
    path: 'tickets',
    component: ViewTicketsComponent
  },
  {
    path: 'ticket/confirm/:ID',
    component: ConfirmationComponent
  },
  {
    path: 'report/select',
    component: BusinessReportSelectComponent
  },
  {
    path: 'report/view',
    component: BusinessReportViewComponent
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
  },
  {
    path: 'friends',
    component: FriendsViewComponent
  },
  {
    path: 'find-friends',
    component: FindFriendsComponent,
  },
  {
    path: 'friendship-requests',
    component: FriendshipRequestsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
