import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrServices } from 'src/app/services/toastr.service';
import { DataService } from 'src/app/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { PagerService } from 'src/app/services/pager.service';
import { MatTableDataSource } from '@angular/material/table'
import { MatSort, Sort } from '@angular/material/sort';
@Component({
  selector: 'app-resortsetuplist',
  templateUrl: './resortsetuplist.component.html',
  styleUrls: ['./resortsetuplist.component.scss'],
  providers: [PagerService]
})
export class ResortsetuplistComponent implements OnInit {
  page: any = 0;
  pageSizeOptions: any = [5, 10, 20, 50, 100];
  rowData: any[] = [];
  url?: string = "";
  rowCount: number = 0;
  data: any;
  pager: any = {};
  pageSize: number = 10;
  addresort: boolean = true;
  dataSource = new MatTableDataSource(this.rowData);
  sortedData: any[] = [];
  displayedColumns: string[] = ['id', 'username', 'name', 'userRole', 'action'];

  constructor(
    private dataService: DataService,
    private toastrService: ToastrServices,
    private pagerService: PagerService,
    private router: Router,
    public dialog: MatDialog) {
    this.sortedData = this.rowData.slice();
  }


  @ViewChild(MatSort, { static: false }) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
    this.getResortsetupList(0)
  }

  /**
  * Get user list
  * @param pageNo
  */
  getResortsetupList(pageNo: number): void {

    this.url = `/api/v1/json/users/findAll?pageNo=${pageNo}&pageSize=${this.pageSize}`;


    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.rowData = res.result.content;
          this.rowCount = res.result.totalElements;
          this.dataSource = new MatTableDataSource(this.rowData);
          this.gridPaging(this.rowCount, pageNo, this.pageSize);
        }
      });
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

  /**
  * Get sort user list
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
        case 'id':
          return this.compare(a.id, b.id, isAsc);
        case 'username':
          return this.compare(a.username, b.username, isAsc);
        case 'name':
          return this.compare(a.name, b.name, isAsc);
        case 'userRole':
          return this.compare(a.userRole, b.userRole, isAsc);
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
  * Filter User list
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
  * Open User add/edit form
  */
  openResort() {
    this.router.navigate(['/admin/resort-add-edit']);
  }

  /**
  * @param event
  */
  pageNavigations(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getResortsetupList(this.page);
  }

  /**
  * redirect to add/update page
  */
  update(element: any) {
    this.router.navigate(['/admin/resort-add-edit/' + element.id]);
  }

  /**
  * Update user
  * @param data
  */
  updateUser(data: any) {
    this.url = '/api/v1/json/user';

    // if (this.userForm?.valid ) {
    this.dataService.putRequestWithObject(this.url, data).subscribe(
      (res: any) => {
        if (res.code == 200) {
          this.toastrService.success('edit');
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


  /**
  * Delete user
  * @param element
  */
  Delete(element: any) {
    this.url = `/api/v1/json/user/`;

    this.dataService.deleteRequest(this.url, element.id)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.toastrService.success('delete');
          this.getResortsetupList(0)
          return true
        } else {
          this.toastrService.failure("Something went Wrong");
          return false;
        }
      });
  }
}
