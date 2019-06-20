import {Component, OnInit} from '@angular/core';
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

  constructor(private router: Router, private registerService: RegisterService) {
  }

  cancel() {
    this.router.navigateByUrl('flight/view');
  }

  login() {
    this.router.navigateByUrl('/signin');
  }

  onSubmit() {
    this.type = this.router.url.substr(8);
    this.registerService.register(this.registerDTO, this.type).subscribe(
      data => {
        window.alert(data);
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
  }

}
