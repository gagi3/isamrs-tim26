import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {TicketService} from '../../../moderation/ticket/ticket.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  location: string;
  ID: any;
  confirmed = false;

  constructor(private router: Router, private service: TicketService) { }
  validate() {
    this.location = this.router.url;
    this.ID = this.location.substr(16);
    this.service.confirm(this.ID).subscribe(
      data => {
        window.alert('Confirmation was successful!');
        this.confirmed = data;
      }, error => {
        window.alert(error.error.errorMessage);
      }
    );
  }
  reroute() {
    this.router.navigateByUrl('/');
  }

  ngOnInit() {
    this.validate();
  }

}
