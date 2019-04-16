import { Component, OnInit } from '@angular/core';
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

  constructor(private router: Router, private registerService: RegisterService) { }

  ngOnInit() {
    this.location = this.router.url;
    this.token = this.location.substr(15);
    this.registerService.validate(this.token).subscribe(
      data => {
        window.alert('Validation was successful!');
        this.router.navigateByUrl('/');
      }, error => {
        window.alert(error.error.errorMessage);
      }
    );
  }

}
