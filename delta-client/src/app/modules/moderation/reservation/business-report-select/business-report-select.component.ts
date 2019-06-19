import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {ProfileService} from '../../../account/profile/shared/service/profile.service';
import {MatDialog, MatDialogConfig} from '@angular/material';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {BusinessReportDTO} from '../business-report-dto';
import {ReservationService} from '../reservation.service';
import {Reservation} from '../../../shared/model/reservation';
import {BusinessReportViewComponent} from '../business-report-view/business-report-view.component';
import {DateTimeFormatPipe} from "../../../shared/date-time-format.pipe";
import {HeaderComponent} from "../../../shared/modules/header/header/header.component";

@Component({
  selector: 'app-business-report-select',
  templateUrl: './business-report-select.component.html',
  styleUrls: ['./business-report-select.component.css']
})
export class BusinessReportSelectComponent implements OnInit {
  admin: AirlineCompanyAdmin;
  dto: BusinessReportDTO = new BusinessReportDTO();
  type = 'daily';
  month = '';
  read = false;
  errorMessage = '';
  username = '';
  reservations: Reservation[] = [];
  @ViewChild('header') header: HeaderComponent;
  showView = 'business-report-select';

  constructor(private router: Router, private tokenStorage: TokenStorageService,
              private profileService: ProfileService, public dialog: MatDialog,
              private service: ReservationService, private datePipe: DateTimeFormatPipe) { }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
    this.dto.before = new Date();
    this.dto.after = new Date();
    this.username = this.tokenStorage.getUsername();
    this.profileService.getAirlineCompanyAdminByUsername(this.username).subscribe(
      data => {
        this.admin = data;
      }
    );
  }
  cancel() {
    this.router.navigateByUrl('');
  }
  onSubmit() {
    this.dto.after = new Date(this.dto.after);
    this.dto.before = new Date(this.dto.before);
    if (this.type === 'daily') {
      this.dto.before = new Date(this.dto.after);
    } else if (this.type === 'weekly') {
      this.dto.before = new Date(this.dto.after);
      this.dto.before.setDate(this.dto.after.getDate() + 7);
    } else if (this.type === 'monthly') {
      switch (this.month) {
        case 'January':
          this.dto.after.setFullYear(2019, 0, 1);
          this.dto.before.setFullYear(2019, 0, 31);
          break;
        case 'February':
          this.dto.after.setFullYear(2019, 1, 1);
          this.dto.before.setFullYear(2019, 1, 28);
          break;
        case 'March':
          this.dto.after.setFullYear(2019, 2, 1);
          this.dto.before.setFullYear(2019, 2, 31);
          break;
        case 'April':
          this.dto.after.setFullYear(2019, 3, 1);
          this.dto.before.setFullYear(2019, 3, 30);
          break;
        case 'May':
          this.dto.after.setFullYear(2019, 4, 1);
          this.dto.before.setFullYear(2019, 4, 31);
          break;
        case 'June':
          this.dto.after.setFullYear(2019, 5, 1);
          this.dto.before.setFullYear(2019, 5, 30);
          break;
        case 'July':
          this.dto.after.setFullYear(2019, 6, 1);
          this.dto.before.setFullYear(2019, 6, 31);
          break;
        case 'August':
          this.dto.after.setFullYear(2019, 7, 1);
          this.dto.before.setFullYear(2019, 7, 31);
          break;
        case 'September':
          this.dto.after.setFullYear(2019, 8, 1);
          this.dto.before.setFullYear(2019, 8, 30);
          break;
        case 'October':
          this.dto.after.setFullYear(2019, 9, 1);
          this.dto.before.setFullYear(2019, 9, 31);
          break;
        case 'November':
          this.dto.after.setFullYear(2019, 10, 1);
          this.dto.before.setFullYear(2019, 10, 30);
          break;
        case 'December':
          this.dto.after.setFullYear(2019, 11, 1);
          this.dto.before.setFullYear(2019, 11, 31);
          break;
        default:
          this.dto.after.setFullYear(2019, 0, 1);
          this.dto.before.setFullYear(2019, 11, 31);
      }
    } else if (this.type === 'yearly') {
      this.dto.after.setFullYear(2019, 0, 1);
      this.dto.before.setFullYear(2019, 11, 31);
    } else {
      console.log('Custom');
    }
    if (this.type !== 'custom') {
      this.dto.after.setHours(0, 0);
      this.dto.before.setHours(23, 59);
    }
    this.dto.after = this.datePipe.transform(this.dto.after);
    this.dto.before = this.datePipe.transform(this.dto.before);
    this.service.businessReport(this.dto).subscribe(
      data => {
        this.reservations = data;
        console.log(data);
        if (this.admin.airlineCompany !== undefined) {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.disableClose = true;
          dialogConfig.autoFocus = true;
          dialogConfig.data = {
            id: 1,
            data
          };
          const dialogRef = this.dialog.open(BusinessReportViewComponent, dialogConfig);
          dialogRef.afterClosed().subscribe(
            result => {
              console.log('Dialog closed.');
              console.log(result);
            }
          );
        } else {
          console.log('The flight does not belong to your company.');
        }
      }
    );
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
