import { Component, OnInit } from '@angular/core';
import {Ticket} from "../../../shared/model/ticket";
import {Flight} from "../../../shared/model/flight";
import {Passenger} from "../../../account/profile/shared/model/passenger";
import {TicketService} from "../../../moderation/ticket/ticket.service";
import {Router} from "@angular/router";
import {TokenStorageService} from "../../../shared/token-storage.service";
import {ProfileService} from "../../../account/profile/shared/service/profile.service";
import {MatDialog} from "@angular/material";
import {FlightService} from "../../../moderation/flight/flight.service";
import {PlaceAndTime} from "../../../shared/model/place-and-time";
import {FriendshipRequestService} from "../../../account/profile/shared/service/friendship-request.service";

@Component({
  selector: 'app-friends-view',
  templateUrl: './friends-view.component.html',
  styleUrls: ['./friends-view.component.css']
})
export class FriendsViewComponent implements OnInit {
  friends: Passenger[] = [];
  username = '';
  passenger: Passenger;

  constructor(private router: Router, private tokenStorage: TokenStorageService, private friendshipService: FriendshipRequestService,
              private profileService: ProfileService, public dialog: MatDialog) { }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
      }
    );
    this.profileService.getFriends().subscribe(
      data => {
        this.friends = data;
      }
    );
  }
  remove(passenger: Passenger) {
    if (this.passenger === undefined) {
      alert('Not logged in!');
    } else {
      this.friendshipService.remove(passenger).subscribe(
        data => {
          if (data === true) {
            alert('You have removed ' + passenger.username + ' from your friends!');
            this.router.navigateByUrl('tickets');
          } else {
            console.log(data);
            alert('Unsuccessful!');
          }
        }
      );
    }
  }

}
