import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from 'src/app/services/data.service';
import { ToastrServices } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginFormControls: any;
  isFormSubmitted = false;
  url?: string;
  error: any = null;
  objCredential: any = null;
  emailAddress: string = '';
  hideop: boolean = false;
  decodeToken: any = {};

  constructor(private formBuilder: FormBuilder,
    private toastrService: ToastrServices,
    private router: Router,
    private dataService: DataService,
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ['', [Validators.required]]
    });
    this.loginFormControls = this.loginForm.controls;
  }
  ngOnInit() {
  }
  onSignInClick() {

    this.isFormSubmitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.objCredential = this.loginForm.value;
    this.signIn();
  }

  //Sign in function
  signIn() {
    // debugger;
    this.url = '/v1/json/login';
    this.router.navigateByUrl('/admin/mis-report');

    if (this.loginForm?.valid) {
      this.dataService.trustedAgentpostRequestWithObject(this.url, this.objCredential).subscribe(
        (result: any) => {
          if (result.code == 200) {
            localStorage.setItem('username', this.objCredential.username);
            localStorage.setItem('token', result.result.token);
            this.router.navigateByUrl('/admin/mis-report');

            return true
          }
          else {
            this.error = result;
            this.toastrService.failure(this.error.message);
            return false;
          }
        });
      return true;
    }
    else {
      return false;
    }
  }
}
