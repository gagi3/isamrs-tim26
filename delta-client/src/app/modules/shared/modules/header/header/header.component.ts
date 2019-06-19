import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  private roles: string[];
  showView = 'unregistered';
  @Output() featureSelected = new EventEmitter<string>();

  constructor(private router: Router, private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_AIRLINECOMPANYADMIN') {
          this.router.navigate(['airline-company-admin']);
          return true;
        } else if (role === 'ROLE_SYSTEMADMIN') {
          this.router.navigate(['system-admin']);
          return true;
        } else if (role === 'ROLE_PASSENGER') {
          this.router.navigate(['passenger']);
          return true;
        }
      });
    }
  }

  airlineCompanyAdminView() {
    this.showView = 'airline-company-admin';
  }

  systemAdminView() {
    this.showView = 'system-admin';
  }

  passengerView() {
    this.showView = 'passenger';
  }

  clickEditCompany() {
    this.featureSelected.emit('edit-company');
    this.router.navigateByUrl('');
  }

  clickAddCompany() {
    this.featureSelected.emit('add-company');
    this.router.navigateByUrl('airline-company/add');
  }

  clickAddAirplane() {
    this.featureSelected.emit('add-airplane');
    this.router.navigateByUrl('airplane/add');
  }

  clickAddFlight() {
    this.featureSelected.emit('add-flight');
    this.router.navigateByUrl('flight/add');
  }

  clickAddPriceList() {
    this.featureSelected.emit('add-price-list');
    this.router.navigateByUrl('price-list/add');
  }

  clickEditPriceList() {
    this.featureSelected.emit('edit-price-list');
    this.router.navigateByUrl('price-list/edit');
  }

  clickFlights() {
    this.featureSelected.emit('flight-view');
    this.router.navigateByUrl('flight/view');
  }

  clickDiscountTickets() {
    this.featureSelected.emit('discount-tickets');
    this.router.navigateByUrl('ticket/discount');
  }

  clickBusinessReport() {
    this.featureSelected.emit('business-report-select');
    this.router.navigateByUrl('report/select');
  }

  clickLogin() {
    this.featureSelected.emit('signin');
    this.router.navigateByUrl('signin');
  }

  clickRegister() {
    this.featureSelected.emit('signup/passenger');
    this.router.navigateByUrl('signup/passenger');
  }

  clickLogout() {
    this.featureSelected.emit('logout');
  }

  clickPProfile() {
    this.featureSelected.emit('profile-view');
    this.router.navigateByUrl('profile/passenger');
  }

  clickACAProfile() {
    this.featureSelected.emit('profile-view');
    this.router.navigateByUrl('profile/airline-company-admin');
  }

  clickSAProfile() {
    this.featureSelected.emit('profile-view');
    this.router.navigateByUrl('profile/system-admin');
  }

  clickDiscountedTickets() {
    this.featureSelected.emit('discounted-tickets-view');
    this.router.navigateByUrl('ticket/discounted');
  }

  clickFriends() {
    this.featureSelected.emit('friends-view');
    this.router.navigateByUrl('friends');
  }

  clickFindFriends() {
    this.featureSelected.emit('find-friends');
    this.router.navigateByUrl('find-friends');
  }

  clickTickets() {
    this.featureSelected.emit('tickets-view');
    this.router.navigateByUrl('tickets');
  }

  clickRequests() {
    this.featureSelected.emit('friendship-requests');
    this.router.navigateByUrl('friendship-requests');
  }
}
