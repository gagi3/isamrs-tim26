import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {Ticket} from '../../../shared/model/ticket';
import {FriendReservationDTO} from '../friend-reservation-dto';
import {Passenger} from '../../../account/profile/shared/model/passenger';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {TicketService} from '../../../moderation/ticket/ticket.service';
import {TokenStorageService} from '../../../shared/token-storage.service';

@Component({
  selector: 'app-ticket-reservation',
  templateUrl: './ticket-reservation.component.html',
  styleUrls: ['./ticket-reservation.component.css']
})
export class TicketReservationComponent implements OnInit {
  ticket: Ticket = new Ticket();
  DTO: FriendReservationDTO = new FriendReservationDTO();
  friend: false;
  friends: Passenger[] = [];
  f: Passenger = new Passenger();
  selectedFriend: Passenger = new Passenger();
  username = '';
  passenger: Passenger = new Passenger();
  reserved = false;


  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: TicketService, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) { }
  cancel() {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.dialogRef.updateSize('150%', '150%');
    this.username = this.tokenStorage.getUsername();
    this.profileService.getPassengerByUsername(this.username).subscribe(
      data => {
        this.passenger = data;
      }
    );
    this.profileService.getFriends().subscribe(
      data => {
        this.friends = data;
        console.log(data);
      }
    );
    this.ticket = this.data.ticket;
    // this.getFriends();
  }
  getFriends() {
    this.profileService.getFriends().subscribe(
      data => {
        this.friends = data;
        console.log(data);
      }
    );
  }
  onSubmit() {
    console.log(this.friend);
    console.log(this.ticket);
    console.log(this.passenger);
    console.log(this.friends);
    console.log(this.selectedFriend);
    console.log(this.DTO);
    if (this.friend !== true) {
      this.service.reserve(this.ticket).subscribe(
        data => {
          alert('Reservation successful.');
          this.reserved = true;
          this.dialogRef.close();
        }, error => {
          alert('Reservation not successful.');
          this.dialogRef.close();
        }
      );
    } else {
      this.DTO.ticket = this.ticket;
      this.DTO.passenger = this.selectedFriend;
      console.log(this.DTO);
      this.service.friendReserve(this.DTO).subscribe(
        data => {
          alert('Reservation successful.');
          this.reserved = true;
          this.dialogRef.close();
        }, error => {
          alert('Reservation not successful.');
          this.dialogRef.close();
        }
      );
    }
  }
}
