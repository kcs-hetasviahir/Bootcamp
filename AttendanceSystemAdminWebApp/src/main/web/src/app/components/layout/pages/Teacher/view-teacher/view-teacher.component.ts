import { Component, OnInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/services/data.service';
import { PageEvent } from '@angular/material/paginator';
import { PagerService } from 'src/app/services/pager.service';
import { MatTableDataSource } from '@angular/material/table'
import { MatSort, Sort } from '@angular/material/sort';
import { ToastrServices } from 'src/app/services/toastr.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-view-teacher',
  templateUrl: './view-teacher.component.html',
  styleUrls: ['./view-teacher.component.scss'],
  providers: [PagerService]
})
export class ViewTeacherComponent implements OnInit {
  url?: string;
  pager: any = {};
  pageSize: number = 10;
  pageNumber: number = 0;
  page: any = 0;
  pageSizeOptions: any = [5, 10, 20, 50, 100];
  name: string = "";
  rowData: any[] = [];
  pageEvent: PageEvent | undefined;
  filterData: Object = {};
  attendance: boolean = false;
  rowCount: number = 0;
  displayedColumns: string[] = ['employeeId', 'registrationId', 'fullName', 'gender', 'dateOfBirth', 'dateOfJoining',
    'mobile', 'email', 'designation', 'superivorName', 'superivorId', 'ssaDistrict', 'ssaBlock', 'ssaSchool', 'id', 'action'];
  dataSource = new MatTableDataSource(this.rowData);
  sortedData: any[] = [];

  constructor(
    private dataService: DataService,
    private pagerService: PagerService,
    private toastrService: ToastrServices,
    private router: Router
  ) { }

  @ViewChild(MatSort, { static: false }) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
    this.getTeacherList(0)
  }

  /**
  * Sort teacher 
  * @param sort
  */
  sortData(sort: Sort) {
    const data = this.rowData.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'employeeId':
          return this.compare(a.employeeId, b.employeeId, isAsc);
        case 'registrationId':
          return this.compare(a.registrationId, b.registrationId, isAsc);
        case 'fullName':
          return this.compare(a.fullName, b.fullName, isAsc);
        case 'gender':
          return this.compare(a.gender, b.gender, isAsc);
        case 'dateOfBirth':
          return this.compare(a.dateOfBirth, b.dateOfBirth, isAsc);
        case 'dateOfJoining':
          return this.compare(a.dateOfJoining, b.dateOfJoining, isAsc);
        case 'mobile':
          return this.compare(a.mobile, b.mobile, isAsc);
        case 'email':
          return this.compare(a.email, b.email, isAsc);
        case 'designation':
          return this.compare(a.designation, b.designation, isAsc);
        case 'superivorName':
          return this.compare(a.superivorName, b.superivorName, isAsc);
        case 'superivorId':
          return this.compare(a.superivorId, b.superivorId, isAsc);
        case 'ssaDistrict':
          return this.compare(a.ssaDistrict, b.ssaDistrict, isAsc);
        case 'ssaBlock':
          return this.compare(a.ssaBlock, b.ssaBlock, isAsc);
        case 'ssaSchool':
          return this.compare(a.ssaSchool, b.ssaSchool, isAsc);
        case 'id':
          return this.compare(a.id, b.id, isAsc);
        default:
          return 0;
      }
    });
    this.dataSource = new MatTableDataSource(this.sortedData);
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  /**
  * filter teacher 
  * @param event
  */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  /**
  * Get Teacher list
  * @param pageNo
  */
  getTeacherList(pageNo: number): void {
    this.url = `/v1/json/teacher/findAll?pageNo=${pageNo}&pageSize=${this.pageSize}`;

    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {

        if (res.code == 200) {
          this.rowData = res.result.content;
          this.rowCount = res.result.totalElements;
          this.dataSource = new MatTableDataSource(this.rowData);
          this.gridPaging(this.rowCount, pageNo, this.pageSize);
        }
      })
  }

  /**
  * List grid pagination
  * @param totalItems 
  * @param currentPage 
  * @param pageSize 
  */
  gridPaging(totalItems: number, currentPage: number, pageSize: number): void {
    this.pager = this.pagerService.getPager(totalItems, currentPage, pageSize);
  }

  updateTeacher(element: any) {
    this.router.navigate(['/admin/add-edit-teacher/' + element.id]);
  }

  /**
  * delete teacher 
  * @param element
  */
  deleteTeacher(element: any) {
    this.url = `/v1/json/teacher/`;

    this.dataService.deleteRequest(this.url, element.id)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.toastrService.success('delete');
          this.getTeacherList(0)
          return true;
        } else {
          this.toastrService.failure("Something went Wrong");
          return false;
        }
      });
  }

  /**
  * @param event
  */
  pageNavigations(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getTeacherList(this.page);
  }

}
