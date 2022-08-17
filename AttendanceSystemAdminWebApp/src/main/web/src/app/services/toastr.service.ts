import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ToastrServices {
  constructor(public toastr: ToastrService) { }

  success(messsage: string) {
    if (messsage == "Email") {
      this.toastr.success("Email send successfully", 'Success!', {
        positionClass: 'toast-top-right',
        timeOut: 3000,
        closeButton: true,
        tapToDismiss: true,
        enableHtml: true
      });
    }
    if (messsage == "edit") {
      this.toastr.success("Record updated successfully", 'Success!', {
        positionClass: 'toast-top-right',
        timeOut: 3000,
        closeButton: true,
        tapToDismiss: true,
        enableHtml: true
      });
    }
    if (messsage == "add") {
      this.toastr.success("Record added successfully", 'Success!', {
        positionClass: 'toast-top-right',
        timeOut: 3000,
        closeButton: true,
        tapToDismiss: true,
        enableHtml: true
      });
    }
    if (messsage == "delete") {
      this.toastr.success("Record deleted successfully", 'Success!', {
        positionClass: 'toast-top-right',
        timeOut: 3000,
        closeButton: true,
        tapToDismiss: true,
        enableHtml: true
      });
    }
  }

  failure(error: string) {
    this.toastr.error(error, 'Error', {
      //  maxOpended: 1,
      positionClass: 'toast-top-right',
      timeOut: 15000,
      closeButton: true,
      tapToDismiss: true,
      enableHtml: true
    });
  }
}
