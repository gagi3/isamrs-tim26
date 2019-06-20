import {Component, OnInit, ViewChild} from '@angular/core';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {FriendshipRequestService} from '../../../account/profile/shared/service/friendship-request.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog} from '@angular/material';
import {FriendshipRequestDTO} from '../friendship-request-dto';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';

@Component({
  selector: 'app-find-friends',
  templateUrl: './find-friends.component.html',
  styleUrls: ['./find-friends.component.css']
})
export class FindFriendsComponent implements OnInit {
  nonFriends: Passenger[] = [];
  username = '';
  passenger: Passenger;
  @ViewChild('header') header: HeaderComponent;
  showView = 'find-friends';

  constructor(private router: Router, private tokenStorage: TokenStorageService, private friendshipService: FriendshipRequestService,
              private profileService: ProfileService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
        this.header.passengerView();
      }
    );
    this.profileService.getNonFriends().subscribe(
      data => {
        this.nonFriends = data;
      }
    );
  }

  add(passenger: Passenger) {
    const dto = new FriendshipRequestDTO();
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      dto.fromID = this.passenger.id;
      dto.toID = passenger.id;
      dto.accept = false;
      dto.delete = false;
      this.friendshipService.create(dto).subscribe(
        data => {
          if (data.accepted === false) {
            alert('You have sent a friendship request to ' + passenger.username + '.');
            this.router.navigateByUrl('find-friends');
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
