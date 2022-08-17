import { DataService } from 'src/app/services/data.service';
import { ToastrServices } from 'src/app/services/toastr.service';
import { Router, NavigationEnd } from '@angular/router';
import { Component, OnInit } from '@angular/core';
// import { ForgotPasswordComponent } from '../../../components/auth/forgot-password/forgot-password.component'
// import { ChangePasswordComponent } from '../../auth/change-password/change-password.component';
import { MatDialog } from '@angular/material/dialog';
// import { ViewprofileComponent } from '../../layout/pages/ViewProfile/viewprofile/viewprofile.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  emailAddress: string | null;
  error: any;
  constructor(
    private router: Router,
    private toastrService: ToastrServices,
    private dataService: DataService,
    public dialog: MatDialog
  ) {
    this.emailAddress = localStorage.getItem("firstname");

  }

  ngOnInit(): void {

  }
  //Click of logout
  logoutClick() {
    const scope = this;
    localStorage.clear();
    scope.router.navigateByUrl('');
    //this.dataService.postRequestWithoutObject('user/logout').subscribe(
    //  (result: any) => {
    //    if (result.code == 200) {
    //      localStorage.clear();
    //      scope.router.navigateByUrl('');
    //    }
    //    else {
    //      this.toastrService.failure('Something went wrong.');
    //    }
    //  },
    //  error => {
    //    this.error = error;
    //    this.toastrService.failure(this.error.statusText);
    //  });
  }

  profileClick() {
    // const dialogRef = this.dialog.open(ViewprofileComponent, {
    //   width: '390px',
    //   data: {
    //     id: 1,
    //     name: "superuser",
    //     email: "admin@gmail.com",
    //     type: 'profile'
    //   }
    // });

    // dialogRef.afterClosed().subscribe((result: any) => {

    // });
  }

  openChangePasswordModel() {
    // const dialogRef = this.dialog.open(ChangePasswordComponent, {});
  }
  status: boolean = false;
  //Click of profile
  profileclick() {
    this.status = !this.status;
    //   $('.welcome-profile-dropdown').toggleClass('welcome-profile-dropdown-open');

  }
  logout(){
    localStorage.clear();
    this.router.navigate(['/']);
  }
}
