import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { EmailData } from 'src/app/models/EmailData';
import { ToastrServices } from 'src/app/services/toastr.service';
import { DataService } from 'src/app/services/data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-email-send',
  templateUrl: './email-send.component.html',
  styleUrls: ['./email-send.component.scss']
})
export class EmailSendComponent implements OnInit {
  url?: string;
  emailForm: FormGroup;
  allowedExtensions: any = ["pdf", "PDF"];
  pdfExtensionError: any;
  pdfSizeError: any;
  filename: string = "";
  files: File[] = [];
  pdffiles: File[] = [];
  emailData: EmailData | undefined;
  pdfrequiredError: any;

  constructor(private toastrService: ToastrServices,
    public emailDialogRef: MatDialogRef<EmailSendComponent>,
    private dataService: DataService,
    private router: Router
  ) {
    this.emailForm = new FormGroup({
      receiverId: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      file: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void { }

  /**
  * pdf file selected
  * @param file
  */
  onPDFSelect(file: any) {
    const fileName = file.addedFiles[0].name;
    const fileSize = file.addedFiles[0].size;

    if (file.addedFiles && file.addedFiles[0]) {
      const reader = new FileReader();
      reader.onload = () => {
        this.emailForm.get('file')?.setValue(file.addedFiles[0]);
      };
      reader.readAsDataURL(file.addedFiles[0]);
    }

    const fileExtension = fileName.split('.').pop();

    if (this.allowedExtensions.includes(fileExtension)) {
      this.pdffiles = [];
      this.pdfExtensionError = false;
      if (fileSize <= 3000000) {
        this.pdfSizeError = false;
        this.pdffiles.push(...file.addedFiles);
      } else {
        this.pdfSizeError = true;
      }
    } else {
      this.pdfExtensionError = true;
    }
  }

  /**
  * pdf file removed
  * @param event
  */
  onPDFRemove(event: File) {
    this.pdffiles.splice(this.files.indexOf(event), 1);
    this.pdfExtensionError = true;
  }

  /**
  * On click get email data
  * @param obj 
  */
  onEmailClick(obj: any) {
    const uploadData = new FormData();
    uploadData.append('receiverId', obj.receiverId);
    uploadData.append('file', obj.file);

    if (obj.file == "") {
      this.pdfExtensionError = true;
    }
    this.sendEmail(uploadData)
  }

  /**
  * send mail functionality
  * @param obj 
  */
  sendEmail(obj: any) {
    this.url = '/v1/json/sendMailWithAttachment';
    if (this.emailForm?.valid && !this.pdfExtensionError && !this.pdfSizeError) {
      this.dataService.postRequestWithObject(this.url, obj)
        .subscribe((res: any) => {

          if (res.code == 200) {
            this.emailDialogRef.close();
            this.router.navigate(['/admin/mis-report']);
            this.toastrService.success('Email');
            return true;
          } else {
            this.toastrService.failure(res.message);
            return false;
          }
        })
    }
  }

  /**
   * Close model
   */
  onCloseClick(): void {
    this.emailDialogRef.close();
  }

  /**
   * Summary: Has error function
   @param controlName
   @param errorName
   */
  public hasError = (controlName: string, errorName: string) => {
    return this.emailForm?.controls[controlName].hasError(errorName);
  }

}
