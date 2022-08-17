import { Component, OnInit, Inject } from '@angular/core';
import { NgxRolesService, NgxPermissionsService } from 'ngx-permissions'
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/services/common.service';
import { DataService } from 'src/app/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  // providers: [Role]
})
export class LayoutComponent implements OnInit {

  emailAddress: string = '';
  url: string = '';
  error: any = '';
  adminRole: string = '';
  userRole: string = '';
  currentRole: string = '';
  partner_type: string = "";
  isShow = false;


  constructor(
    @Inject(DOCUMENT) private document: Document,
    public dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  checkWord(word: any, str: any) {
    const allowedSeparator = '\\\s,;"\'|';

    const regex = new RegExp(
      `(^.*[${allowedSeparator}]${word}$)|(^${word}[${allowedSeparator}].*)|(^${word}$)|(^.*[${allowedSeparator}]${word}[${allowedSeparator}].*$)`,

      // Case insensitive
      'i',
    );

    return regex.test(str);
  }
  /* 
    (function(){
      $('#usermamagement').on('click', function(){
        $('.dropdown-menu').toggleClass('show');
      });
    }()); */

  dropdownOpen() {
    let DATA: any = document.getElementById('usermamagement');
    this.isShow = !this.isShow;

    // $('.dropdown-menu').toggleClass('show');
  }

  hamburger_cross: boolean = false;
  Menu: boolean = false;
  //Click of profile
  hamburgerclick() {
    this.hamburger_cross = !this.hamburger_cross;
    this.Menu = !this.Menu;
    //   $('.welcome-profile-dropdown').toggleClass('welcome-profile-dropdown-open');

  }
}
