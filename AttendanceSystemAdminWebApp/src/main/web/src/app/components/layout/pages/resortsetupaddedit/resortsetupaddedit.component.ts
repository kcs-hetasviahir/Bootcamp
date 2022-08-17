import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl, FormBuilder } from '@angular/forms';
import { ToastrServices } from 'src/app/services/toastr.service';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-resortsetupaddedit',
  templateUrl: './resortsetupaddedit.component.html',
  styleUrls: ['./resortsetupaddedit.component.scss']
})

export class ResortsetupaddeditComponent implements OnInit {
  url?: string = "";
  userForm: FormGroup;
  isUpdateForm: boolean = false;
  resortId: string = "";
  editId: any;
  actionvisible: boolean = true;
  activityadd: boolean = true;
  activityedit: boolean = true;
  designationList: any[] = [];
  isdata: boolean = true;
  username: string = "";
  userData: any[] = []

  constructor(private dataService: DataService,
    private toastrService: ToastrServices,
    private router: Router,
    public dialog: MatDialog,
    private route: ActivatedRoute,
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    }
    this.getResortTypeList();
    this.userForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      userRole: new FormControl('ADMIN', [Validators.required]),
      username: new FormControl('', [Validators.required]),
      status: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),

    });
    var data = localStorage.getItem("userrole");
    if (data == "ResortAdmin") {
      this.actionvisible = false;

      var data = localStorage.getItem("permission");
      var permissiondata = JSON.parse(data!);
      if (permissiondata.newActivity == true) {
        this.activityadd = true;
      }
      else {
        this.activityadd = false;
      }
      if (permissiondata.existingActivity == true) {
        this.activityedit = true;
      }
      else {
        this.activityedit = false;
      }
    }
  }

  ngOnInit(): void {
    this.editId = this.route.snapshot.params["resortId"];
    if (this.editId) {
      this.getData(this.editId);
    }
  }


  /**
  * Get designation list
  */
  getResortTypeList() {
    this.url = '/v1/json/designation';
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.designationList = res.result;
          this.userForm.get('status')?.setValue(1);
        }
      });
  }


  /**
  * Has error function
  @param controlName
  @param errorName
  */
  public hasError = (controlName: string, errorName: string) => {
    return this.userForm?.controls[controlName].hasError(errorName);
  }

  /**
  * Get data of particular user
  @param id
  */
  getData(id: any) {
    this.url = '/api/v1/json/user/' + id;

    this.dataService.getDataById(this.url)
      .subscribe((result: any) => {

        if (result.code == 200) {
          this.userData = result.result;

          this.editId = result.result["id"];
          this.userForm.patchValue(result.result);
          this.userForm.get("resortName")?.setValue(result.result["name"]);
          this.userForm.get("userRole")?.setValue(result.result["userRole"]);
          this.userForm.get("username")?.setValue(result.result["username"]);
          this.userForm.get("password")?.setValue(result.result["password"]);


          if (result.result["name"] == "@Home") {
            this.isdata = false;
          }
          this.isUpdateForm = this.editId ? true : false;

        }
      });
  }

  /**
  * Add/Update User
  * @param obj
  */
  onUserClick(obj: any) {

    obj.id = this.editId;

    let postObjData = {

      name: obj.name,
      userRole: obj.userRole,
      username: obj.username,
      password: obj.password
    }

    let postObjDataEdit = {

      id: obj.id,
      name: obj.name,
      userRole: obj.userRole,
      username: obj.username
    }
    if (!this.isUpdateForm) {
      this.username = obj.name;
      this.addUser(postObjData);
    }
    if (this.isUpdateForm) {
      this.updateUser(postObjDataEdit);
    }

  }

  /**
  * Close model
  */
  onCloseClick(): void {
    this.router.navigate(['/admin/user-management']);
  }

  /**
  * Update User
  * @param data
  */
  updateUser(data: any) {
    this.url = '/api/v1/json/user';

    this.dataService.putRequestWithObject(this.url, data).subscribe(
      (res: any) => {
        if (res.code == 200) {

          this.editId = res.result["id"];
          this.getData(this.editId);
          this.toastrService.success('edit');

          this.router.navigate(['/admin/user-management']);
          return true;
        }
        else {
          this.toastrService.failure("Something went Wrong");
          return false;
        }
      });
  }

  /**
  * Add User
  * @param data
  */
  addUser(data: any) {
    this.url = '/api/v1/json/user';
    if (this.userForm?.valid) {

      this.dataService.postRequestWithObject(this.url, data).subscribe(
        (res: any) => {
          if (res.code == 200) {
            this.editId = res.result["id"];
            this.toastrService.success('add');
            this.router.navigate(['/admin/user-management']);
            return true;
          }
          else {
            this.toastrService.failure("Something went Wrong");
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
