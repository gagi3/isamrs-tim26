import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {ReservationService} from '../reservation.service';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {Reservation} from '../../../shared/model/reservation';

@Component({
  selector: 'app-business-report-view',
  templateUrl: './business-report-view.component.html',
  styleUrls: ['./business-report-view.component.css']
})
export class BusinessReportViewComponent implements OnInit {
  reservations: Reservation[] = [];
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  profit = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<any>, private router: Router,
              private service: ReservationService, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog) { }

  cancel() {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.dialogRef.updateSize('100%', '100%');
    this.username = this.tokenStorage.getUsername();
    this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
      }
    );
    this.read();
    this.reservations = this.data.data;
  }
  read() {
    this.mapFlights();
    this.calcProfit();
  }
  mapFlights() {
    for (const r of this.data.data) {
      this.service.getFlight(r.id).subscribe(
        data => {
          r.ticket.flight = data;
        }
      );
    }
  }
  calcProfit() {
    for (const reservation of this.data.data) {
      this.profit += reservation.ticket.price;
    }
  }

}
