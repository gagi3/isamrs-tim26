import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RoleGuardService} from '../../shared/role-guard.service';
import {httpInterceptorProviders} from '../../shared/auth-interceptor';
import {MatDialogModule, MatSelectModule} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FriendsViewComponent} from './friends-view/friends-view.component';
import {FindFriendsComponent} from './find-friends/find-friends.component';
import {FriendshipRequestsComponent} from './friendship-requests/friendship-requests.component';
import {HeaderModule} from '../../shared/modules/header/header.module';

@NgModule({
  declarations: [FriendsViewComponent, FindFriendsComponent, FriendshipRequestsComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    BrowserAnimationsModule,
    HeaderModule
  ],
  providers: [RoleGuardService, httpInterceptorProviders, MatDialogModule],
})
export class InteractionModule {
}
