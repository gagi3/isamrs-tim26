import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RegisterService} from '../register.service';
import {RegisterDTO} from '../register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerDTO: RegisterDTO = new RegisterDTO();
  type: string;
  isRegistered = false;
  failed = false;
  errorMessage = '';

  constructor(private router: Router, private registerService: RegisterService) { }
  cancel() {
    this.router.navigateByUrl('');
  }
  onSubmit() {
    this.type = this.router.url.substr(8);
    console.log('Type: ' + this.type);
    this.registerService.register(this.registerDTO, this.type).subscribe(
      data => {
        window.alert('Registration was successful! Please check your email.');
        this.isRegistered = true;
        this.failed = false;
      }, error => {
        console.log(error);
        this.errorMessage = error.error.errorMessage;
        this.isRegistered = false;
        this.failed = true;
        window.alert(this.errorMessage);
      }
    );
  }

  ngOnInit() {
    this.onSubmit();
  }

}
