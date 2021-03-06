import { Component, OnInit } from '@angular/core';
import { UserSignUp } from '../../../models/UserSignUp';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import {UserSignUpService} from '../../../services/user-sign-up.service';
@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  userSignUp: UserSignUp;
  firstNameErrorMessageBackEnd: string;
  lastNameErrorMessageBackEnd: string;
  emailErrorMessageBackEnd: string;
  passwordErrorMessageBackEnd: string;
  loadingAnim = false;

  constructor(
    private userSecurityService: UserSignUpService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.userSignUp = new UserSignUp();
    this.setNullAllMessage();
  }

  private register(userSignUp: UserSignUp) {
    this.setNullAllMessage();
    this.loadingAnim = true;
    this.userSecurityService.signUp(userSignUp).subscribe(
      () => {
        this.router.navigateByUrl('login').then(r => r);
      },
      (errors: HttpErrorResponse) => {
          this.passwordErrorMessageBackEnd = 'Incorrect Data';
          this.loadingAnim = false;
        });
    this.loadingAnim = false;
  }

  private setNullAllMessage() {
    this.firstNameErrorMessageBackEnd = null;
    this.lastNameErrorMessageBackEnd = null;
    this.emailErrorMessageBackEnd = null;
    this.passwordErrorMessageBackEnd = null;
  }
}
