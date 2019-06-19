import {Component, OnInit, ViewChild} from '@angular/core';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {FriendshipRequestService} from '../../../account/profile/shared/service/friendship-request.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog} from '@angular/material';
import {FriendshipRequest} from '../../../shared/model/friendship-request';
import {FriendshipDTO} from '../friendship-dto';
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

@Component({
  selector: 'app-friendship-requests',
  templateUrl: './friendship-requests.component.html',
  styleUrls: ['./friendship-requests.component.css']
})
export class FriendshipRequestsComponent implements OnInit {
  sent: FriendshipRequest[] = [];
  received: FriendshipRequest[] = [];
  username = '';
  passenger: Passenger;
  show = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'friendship-requests';
  constructor(private router: Router, private tokenStorage: TokenStorageService, private friendshipService: FriendshipRequestService,
              private profileService: ProfileService, public dialog: MatDialog) { }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
        this.header.passengerView();
      }
    );
    this.friendshipService.getSent().subscribe(
      data => {
        this.sent = data;
      }
    );
    this.friendshipService.getReceived().subscribe(
      data => {
        this.received = data;
      }
    );
  }
  reject(passenger: Passenger) {
    const dto = new FriendshipDTO();
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      dto.from = this.passenger;
      dto.to = passenger;
      this.friendshipService.reject(dto).subscribe(
        data => {
          if (data === true) {
            alert('You have rejected a friendship request from ' + passenger.username + '!');
            this.router.navigateByUrl('tickets');
          } else {
            console.log(data);
            alert('Unsuccessful!');
          }
        }
      );
    }
  }
  accept(req: FriendshipRequest) {
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      this.friendshipService.accept(req).subscribe(
        data => {
          if (data === true) {
            alert('You have accepted a friendship request from ' + req.sentTo.username + '!');
            this.router.navigateByUrl('tickets');
          } else {
            console.log(data);
            alert('Unsuccessful!');
          }
        }
      );
    }
  }
  onNavigate(feature: string) {
    console.log(feature);
    this.showView = feature;
    if (feature === 'logout') {
      window.sessionStorage.clear();
      this.router.navigate(['']);
      window.alert('Successfully Logged out!');
    }
  }

}
