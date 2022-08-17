import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { DataService } from 'src/app/services/data.service';
import { ToastrServices } from 'src/app/services/toastr.service';
import * as moment from 'moment';

@Component({
  selector: 'app-add-edit-teacher',
  templateUrl: './add-edit-teacher.component.html',
  styleUrls: ['./add-edit-teacher.component.scss']
})
export class AddEditTeacherComponent implements OnInit {

  isUpdateForm: boolean = false;
  actionvisible: boolean = true;
  teacherForm: FormGroup;
  isdata: boolean = true;
  editId: any;
  distId: any;
  blockId: any;
  userData: any[] = [];
  url?: string = "";
  designationList: Array<any> = [];
  distictList: Array<any> = [];
  blockList: Array<any> = [];
  schoolList: Array<any> = [];

  constructor(private router: Router, private dataService: DataService,
    private toastrService: ToastrServices,
    private route: ActivatedRoute
  ) {
    this.getDesignation();
    this.teacherForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      middleName: new FormControl(''),
      lastName: new FormControl('', [Validators.required]),
      dateOfBirth: new FormControl('', [Validators.required]),
      dateOfJoining: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      houseName: new FormControl(''),
      address1: new FormControl(''),
      address2: new FormControl(''),
      pincode: new FormControl('', [Validators.required]),
      mobile: new FormControl(''),
      email: new FormControl(''),
      district: new FormControl('', [Validators.required]),
      taluka: new FormControl('', [Validators.required]),
      village: new FormControl('', [Validators.required]),
      designation: new FormControl('', [Validators.required]),
      ssaDistrict: new FormControl('', [Validators.required]),
      ssaBlock: new FormControl('', [Validators.required]),
      ssaSchool: new FormControl('', [Validators.required]),

    });
    this.teacherForm?.controls['mobile'].disable();
    this.teacherForm?.controls['email'].disable();

  }

  /**
  * Has error function
  @param controlName
  @param errorName
  */
  public hasError = (controlName: string, errorName: string) => {
    return this.teacherForm?.controls[controlName].hasError(errorName);
  }


  ngOnInit(): void {
    this.editId = this.route.snapshot.params["id"];
    if (this.editId) {
      this.getData(this.editId);
    }
    this.getDistrict();

  }

  /**
   * Get data of a teacher
   @param id
   */
  getData(id: any) {
    this.url = '/v1/json/teacher?teacherId=' + id;
    this.dataService.getDataById(this.url)
      .subscribe((result: any) => {
        if (result.code == 200) {
          this.userData = result.result;

          this.getBlock(result.result["ssaDistrict"].split('-')[0]);
          this.getSchool(result.result["ssaBlock"].split('-')[0]);
          this.getDesignation();
          this.editId = result.result["id"];
          this.teacherForm.patchValue(result.result);
          this.teacherForm.get("firstName")?.setValue(result.result["firstName"]);
          this.teacherForm.get("middleName")?.setValue(result.result["middleName"]);
          this.teacherForm.get("lastName")?.setValue(result.result["lastName"]);
          this.teacherForm.get("dateOfBirth")?.setValue(moment(result.result['dateOfBirth'], 'DD-MM-YYYY').format('YYYY-MM-DD'));
          this.teacherForm.get("dateOfJoining")?.setValue(moment(result.result['dateOfJoining'], 'DD-MM-YYYY').format('YYYY-MM-DD'));
          this.teacherForm.get("gender")?.setValue(result.result["gender"]);
          this.teacherForm.get("houseName")?.setValue(result.result["houseName"]);
          this.teacherForm.get("address1")?.setValue(result.result["address1"]);
          this.teacherForm.get("address2")?.setValue(result.result["address2"]);
          this.teacherForm.get("district")?.setValue(result.result["district"]);
          this.teacherForm.get("taluka")?.setValue(result.result["taluka"]);
          this.teacherForm.get("village")?.setValue(result.result["village"]);
          this.teacherForm.get("pincode")?.setValue(result.result["pincode"]);
          this.teacherForm.get("mobile")?.setValue(result.result["mobile"]);
          this.teacherForm.get("email")?.setValue(result.result["email"]);
          this.teacherForm.get("designation")?.setValue((result.result["designation"].split('-')[9]));
          this.teacherForm.get("ssaDistrict")?.setValue(parseInt(result.result["ssaDistrict"].split('-')[0]));
          this.teacherForm.get("ssaBlock")?.setValue(parseInt(result.result["ssaBlock"].split('-')[0]));
          this.teacherForm.get("ssaSchool")?.setValue(parseInt(result.result["ssaSchool"].split('-')[0]));

          if (result.result["name"] == "@Home") {
            this.isdata = false;
          }
          this.isUpdateForm = this.editId ? true : false;

        }
      });
  }


  onCloseClick(): void {
    this.router.navigate(['/admin/view-teacher']);
  }

  /**
  * Get Designation
  */
  getDesignation() {
    this.url = `/v1/json/designation`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.designationList = res.result;
          this.teacherForm.get('designation')?.setValue(9);

        }
      });
  }

  /**
  * Get District
  */
  getDistrict() {
    this.url = '/v1/json/ssaDistricts';
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.distictList = res.result;
        }
      });
  }

  /**
  * Get Block
  */
  getBlock(distId: string) {
    this.url = `/v1/json/ssaBlocks/?distId=${distId}`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.blockList = res.result;
        }
      });
  }

  distictSelectedValue(event: any) {
    this.teacherForm.patchValue({
      ssaBlock: '',
      ssaSchool: ''
    });
    this.getBlock(event.value)
  }

  blockSelectedValue(event: any) {
    this.teacherForm.patchValue({
      ssaSchool: ''
    });
    this.getSchool(event.value)
  }

  /**
  * Get School
  */
  getSchool(blockId: string) {
    this.url = `/v1/json/ssaSchools/?blockId=${blockId}`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.schoolList = res.result;
        }
      });
  }

  /**
  * Update User
  * @param data
  */
  updateUser(data: any) {
    this.url = '/v1/json/teacher';

    // if (this.teacherForm?.valid) {
    this.dataService.putRequestWithObject(this.url, data).subscribe(
      (res: any) => {
        if (res.code == 200) {

          this.editId = res.result["id"];
          this.getData(this.editId);
          this.toastrService.success('edit');

          this.router.navigate(['/admin/view-teacher']);
          return true;
        }
        else {
          this.toastrService.failure("Something went Wrong");
          return false;
        }
      });
    // }
  }

  /**
  * Get particular teacher data
  * @param obj
  */
  onTeacherClick(obj: any) {
    obj.id = this.editId;

    let postObjDataEdit = {
      id: obj.id,
      firstName: obj.firstName,
      middleName: obj.middleName,
      lastName: obj.lastName,
      dateOfBirth: obj.dateOfBirth,
      dataOfJoining: obj.dataOfJoining,
      address1: obj.address1,
      address2: obj.address2,
      mobile: obj.mobile,
      email: obj.email,
      designation: obj.designation,
      houseName: obj.houseName,
      pincode: obj.pincode,
      district: obj.district,
      taluka: obj.taluka,
      village: obj.village,
      ssaDistrict: obj.ssaDistrict,
      ssaBlock: obj.ssaBlock,
      ssaSchool: obj.ssaSchool,
      gender: obj.gender
    }

    let formData = this.teacherForm.value;

    if (this.isUpdateForm || formData.dateOfBirth || formData.dateOfJoining) {
      formData.dateOfBirth = moment(formData.dateOfBirth).format('DD-MM-YYYY')
      formData.dateOfJoining = moment(formData.dateOfJoining).format('DD-MM-YYYY')
      this.updateUser(postObjDataEdit);
    }
  }



}
