import {Component, Inject, OnInit} from '@angular/core';
import {LoginDTO} from '../login.dto';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {LoginService} from '../login.service';
import {TokenStorageService} from '../../../shared/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  failed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginDTO: LoginDTO;

  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<any>,
              private loginService: LoginService, private tokenStorage: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }
  onSubmit() {
    this.loginDTO = new LoginDTO(this.form.username, this.form.password);
    this.loginService.attemptAuth(this.loginDTO).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.isLoggedIn = true;
        this.failed = false;
        this.roles = this.tokenStorage.getAuthorities();
        this.dialogRef.close();
        this.roles.every( role => {
          if (role === 'ROLE_AIRLINECOMPANYADMIN') {
            this.router.navigateByUrl('administration');
            return true;
          } else if (role === 'ROLE_PASSENGER') {
            this.router.navigateByUrl('consumption');
            return true;
          } else {
            this.router.navigateByUrl('moderation');
            return true;
          }
        });
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.errorMessage;
        this.failed = true;
      }
    );
  }

  reload() {
    window.location.reload();
  }

}
