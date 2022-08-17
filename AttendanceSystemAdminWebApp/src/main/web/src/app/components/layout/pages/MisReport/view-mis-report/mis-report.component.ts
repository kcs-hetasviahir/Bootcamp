import { Component, OnInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/services/data.service';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import * as moment from 'moment'
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { PageEvent } from '@angular/material/paginator';
import { PagerService } from 'src/app/services/pager.service';
import { EmailSendComponent } from '../email-send/email-send.component';
import { MatTableDataSource } from '@angular/material/table'
import { MatSort, Sort } from '@angular/material/sort';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-mis-report',
  templateUrl: './mis-report.component.html',
  providers: [PagerService],
  styleUrls: ['./mis-report.component.scss']
})
export class MisReportComponent implements OnInit {
  url?: string;
  distictList: Array<any> = [];
  blockList: Array<any> = [];
  clusterList: Array<any> = [];
  schoolList: Array<any> = [];
  designationList: Array<any> = [];
  misReportForm: any;
  pager: any = {};
  pageSize: number = 10;
  page: any = 0;
  name: string = "";
  rowData: any[] = [];
  pageEvent: PageEvent | undefined;
  pageSizeOptions: any = [5, 10, 20, 50, 100];
  excelFileName = `report_${Date.now().toString()}.xlsx`;
  pdfFileName = `report_${Date.now().toString()}.pdf`;
  filterData: Object = {};
  attendance: boolean = false;
  rowCount: number = 0;
  displayedColumns: string[] = ['id', 'teacherName', 'attendanceDate', 'createdDate', 'attendanceStatus', 'transactionId', 'deviceId', 'latitude', 'longitude', 'accuracy'];
  dataSource = new MatTableDataSource(this.rowData);
  sortedData: any[] = [];
  distictValue: any;
  blockValue: any;
  clusterValue: any;
  selectedPageSize: string = "";
  leaderpageSize: number = 10;

  constructor(private dataService: DataService,
    private formBuilder: FormBuilder,
    private pagerService: PagerService,
    public dialog: MatDialog) {
    this.sortedData = this.rowData.slice();
  }

  @ViewChild(MatSort, { static: false }) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
    this.misReportForm = this.formBuilder.group({
      employeeId: '',
      ssaDistrict: [0],
      ssaBlock: [0],
      ssaCluster: [0],
      ssaSchool: [0],
      supervisorName: '',
      fromDate: '',
      toDate: '',
      designation: [0],
      attendanceStatus: ''
    });

    this.getDistrict();
    this.getDesignation();
    this.selectedPageSize = this.leaderpageSize.toString();
    this.getMisReportList(0)

  }

  /**
   * Sort Mis Report list
   * @param event
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
        case 'teacherName':
          return this.compare(a.teacherName, b.teacherName, isAsc);
        case 'attendanceDate':
          return this.compare(a.attendanceDate, b.attendanceDate, isAsc);
        case 'attendanceStatus':
          return this.compare(a.attendanceStatus, b.attendanceStatus, isAsc);
        case 'accuracy':
          return this.compare(a.accuracy, b.accuracy, isAsc);
        case 'latitude':
          return this.compare(a.latitude, b.latitude, isAsc);
        case 'longitude':
          return this.compare(a.longitude, b.longitude, isAsc);
        case 'deviceId':
          return this.compare(a.deviceId, b.deviceId, isAsc);
        case 'createdDate':
          return this.compare(a.createdDate, b.createdDate, isAsc);
        case 'transactionId':
          return this.compare(a.transactionId, b.transactionId, isAsc);
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
  * Filter Mis Report list
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
  * Get Mis Report list
  * @param pageNo
  */
  getMisReportList(pageNo: number): void {
    this.url = '/v1/json/teacher-attendance-mis';

    let data =
    {
      "pageNo": pageNo,
      "pageSize": this.pageSize,
      "attendanceStatus": ""
    }
    data = Object.assign(data, this.filterData)

    this.dataService.postRequestWithObject(this.url, data)
      .subscribe((res: any) => {

        if (res.code == 200) {

          if (!this.attendance) {
            this.rowData = res.result.content;
            this.rowCount = res.result.totalElements;
            this.dataSource = new MatTableDataSource(this.rowData);
            this.gridPaging(this.rowCount, pageNo, this.pageSize);
          } else {
            this.rowData = res.result.content.filter((data: any) => data.attendanceStatus !== 'PRESENT');
            this.rowCount = res.result.content.filter((data: any) => data.attendanceStatus !== 'PRESENT').length;
            this.dataSource = new MatTableDataSource(this.rowData);
            this.attendance = false;
            this.gridPaging(this.rowCount, pageNo, this.pageSize);
          }
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

  /**
  * Get Distict List
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
  * Get Block List
  */
  getBlock(distictId: string) {
    this.url = `/v1/json/ssaBlocks/?distId=${this.distictValue}`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.blockList = res.result;
        }
      });
    this.getCluster();
  }

  /**
  * Get Cluster List
  */
  getCluster(blockId?: string) {
    this.url = `/v1/json/ssaClusters?distId=${this.distictValue}&blockId=${this.blockValue}`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.clusterList = res.result;
        }
      });
    this.getSchool()
  }

  /**
  * Get School List
  */
  getSchool(distId?: string, blockId?: string, clusterId?: string) {
    this.url = `/v1/json/ssaSchools?distId=${this.distictValue}&blockId=${this.blockValue}&crcId=${this.clusterValue}`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.schoolList = res.result;
        }
      });
  }

  /**
  * Get Designation List
  */
  getDesignation() {
    this.url = `/v1/json/designation`;
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.designationList = res.result;
        }
      });
  }

  distictSelectedValue(event: any) {
    this.distictValue = event.value;
    this.misReportForm.patchValue({
      ssaBlock: 0,
      ssaCluster: 0,
      ssaSchool: 0
    });
    this.getBlock(event.value)
  }

  blockSelectedValue(event: any) {
    this.blockValue = event.value;
    this.misReportForm.patchValue({
      ssaCluster: 0,
      ssaSchool: 0
    });
    this.getCluster(event.value)
  }

  clusterSelectedValue(event: any) {
    this.clusterValue = event.value;
    this.misReportForm.patchValue({
      ssaSchool: 0
    });
    this.getSchool(event.value)
  }

  onFormSubmit() {
    if (this.misReportForm.valid) {
      let formData = this.misReportForm.value;

      formData.ssaDistrict == 0 ? '' : formData.ssaDistrict;
      formData.ssaBlock == 0 ? '' : formData.ssaBlock;
      formData.ssaSchool == 0 ? '' : formData.ssaSchool;
      formData.designation == 0 ? '' : '';
      if (formData.ssaDistrict == 0) {
        formData.ssaDistrict = '';
      }
      if (formData.ssaBlock == 0) {
        formData.ssaBlock = '';
      }
      if (formData.ssaSchool == 0) {
        formData.ssaSchool = '';
      }
      if (formData.designation == 0) {
        formData.designation = '';
      }
      if (formData.fromDate) {
        formData.fromDate = moment(formData.fromDate).format('DD-MM-YYYY')
      }
      if (formData.toDate) {
        formData.toDate = moment(formData.toDate).format('DD-MM-YYYY')
      }
      if (formData.attendanceStatus === 'ABSENT') {
        this.attendance = true;
        formData.attendanceStatus = ''
      }
      this.filterData = formData
      this.getMisReportList(0)
    }
  }

  resetForm() {
    this.misReportForm.patchValue({
      employeeId: '',
      ssaDistrict: 0,
      ssaBlock: 0,
      ssaCluster: 0,
      ssaSchool: 0,
      supervisorName: '',
      fromDate: '',
      toDate: '',
      designation: 0,
      attendanceStatus: ''
    });
  }

  /**
  * Generate PDF
  */
  generatePDF(divId: any) {
    let DATA: any = document.getElementById(divId);
    html2canvas(DATA).then((canvas: any) => {
      let fileWidth = 208;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 0;
      PDF.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight);
      PDF.save(this.pdfFileName);
    });
    return false;
  }

  /**
  * Generate Print
  */
  print() {
    let printContents, popupWin: any;
    printContents = document.getElementById('print-content')?.innerHTML;
    popupWin = window.open('', '_blank', '');
    popupWin.document.open();
    popupWin.document.write(`<html>
    <head>
      <title>Report</title>
      <style>
      #non-printable {
        display: none !important;
      }
      table,td, th {
        border: 1px solid #ddd;
        padding: 8px;
      }
      </style>
    </head>
    <body onload="window.print();window.close()">${printContents}</body>
    </html>`
    );
    popupWin.document.close();
    return false;
  }

  /**
  * Generate Excel
  */
  generateExcel() {
    let element = document.getElementById('table-data');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    /* generate workbook and add the worksheet */
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    /* save to file */
    XLSX.writeFile(wb, this.excelFileName);
    return false;
  }

  openEmailModal() {
    const dialogRef = this.dialog.open(EmailSendComponent, {
      width: '550px',
      data: {
        name: this.name,
      }
    });
    dialogRef.disableClose = true
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  /**
  * @param event
  */
  pageNavigations(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getMisReportList(this.page);
  }
}




