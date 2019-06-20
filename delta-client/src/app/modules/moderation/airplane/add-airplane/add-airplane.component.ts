import {Component, OnInit, ViewChild} from '@angular/core';
import {AirplaneDTO} from '../airplane-dto';
import {Router} from '@angular/router';
import {AirplaneService} from '../airplane.service';
import {SeatDTO} from '../seat-dto';
import {SeatClass} from '../../../shared/enumeration/seat-class.enum';
import {AirlineCompanyAdmin} from '../../../account/profile/shared/model/airline-company-admin';
import {HeaderComponent} from '../../../shared/modules/header/header/header.component';


@Component({
  selector: 'app-add-airplane',
  templateUrl: './add-airplane.component.html',
  styleUrls: ['./add-airplane.component.css']
})
export class AddAirplaneComponent implements OnInit {
  dto: AirplaneDTO = new AirplaneDTO();
  username = '';
  admin: AirlineCompanyAdmin = new AirlineCompanyAdmin();
  added = false;
  failed = false;
  rows = 0;
  cols = 0;
  row = [];
  seatClass: SeatClass;
  errorMessage = '';
  @ViewChild('header') header: HeaderComponent;
  showView = 'add-airplane';

  constructor(private router: Router, private service: AirplaneService) {
  }

  cancel() {
    this.router.navigateByUrl('flight/view');
  }

  ngOnInit() {
    this.header.airlineCompanyAdminView();
  }

  create() {
    const row = [];
    let rowSeats = [];
    this.dto.seats = [];
    if (this.rows > 40 || this.cols > 6) {
      alert('There must not be more than 40 rows and 6 columns!');
    } else {
      for (let i = 0; i < this.rows; i++) {
        for (let j = 0; j < this.cols; j++) {
          const seat = new SeatDTO(i + 1, j + 1, SeatClass.ECONOMY);
          this.dto.seats.push(seat);
          rowSeats.push(seat);
        }
        row.push(rowSeats);
        rowSeats = [];
      }
      this.row = row;
    }
  }

  onSubmit() {
    this.service.add(this.dto).subscribe(
      data => {
        window.alert('Registration was successful!');
        this.added = true;
        this.failed = false;
        this.router.navigateByUrl('flight/view');
      }, error => {
        console.log(error);
        this.errorMessage = error.errorMessage;
        this.added = false;
        this.failed = true;
        window.alert(this.errorMessage);
      }
    );
  }

  refresh() {
    if (this.cols === 6) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '11.1%');
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '11.1%');
    } else if (this.cols === 5) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '14.28571428571429%');
      document.getElementsByClassName('seat').item(3).setAttribute('margin-right', '14.28571428571429%');
    } else if (this.cols === 4) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '20.28571428571429%');
    } else if (this.cols === 3) {
      document.getElementsByClassName('seat').item(1).setAttribute('margin-right', '26.28571428571429%');
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '26.28571428571429%');
    } else if (this.cols === 2) {
      document.getElementsByClassName('seat').item(2).setAttribute('margin-right', '32.28571428571429%');
    } else if (this.cols === 1) {
      document.getElementsByClassName('seat').item(0).setAttribute('margin-right', '44.28571428571429%');
    }
  }

  seatAction(seat, seatClass) {
    if (seatClass.toString() === 'ECONOMY') {
      seat.seatClass = SeatClass.ECONOMY;
      document.getElementById('seat-label-' + seat.rowNo + '-' + seat.colNo).style.border = '3px solid #993399';
    } else if (seatClass.toString() === 'BUSINESS') {
      seat.seatClass = SeatClass.BUSINESS;
      document.getElementById('seat-label-' + seat.rowNo + '-' + seat.colNo).style.border = '3px solid #3385ff';
    } else if (seatClass.toString() === 'FIRST') {
      seat.seatClass = SeatClass.FIRST;
      document.getElementById('seat-label-' + seat.rowNo + '-' + seat.colNo).style.border = '3px solid #00ffa5';
    } else {
      document.getElementById('seat-label-' + seat.rowNo + '-' + seat.colNo).style.border = '3px solid #f1f1f1';
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
