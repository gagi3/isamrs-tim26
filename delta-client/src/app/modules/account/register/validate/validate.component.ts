import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RegisterService} from '../register.service';

@Component({
  selector: 'app-validate',
  templateUrl: './validate.component.html',
  styleUrls: ['./validate.component.css']
})
export class ValidateComponent implements OnInit {

  location: string;
  token: string;
  validated = false;

  constructor(private router: Router, private registerService: RegisterService) {
  }

  validate() {
    this.location = this.router.url;
    this.token = this.location.substr(16);
    this.registerService.validate(this.token).subscribe(
      data => {
        window.alert('Validation was successful!');
        this.validated = true;
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
