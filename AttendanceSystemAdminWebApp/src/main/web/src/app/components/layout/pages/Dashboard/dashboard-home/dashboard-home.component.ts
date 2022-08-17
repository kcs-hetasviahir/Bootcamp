import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { Chart } from 'chart.js';
import { DataService } from 'src/app/services/data.service';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-dashboard-home',
  templateUrl: './dashboard-home.component.html',
  styleUrls: ['./dashboard-home.component.scss']
})
export class DashboardHomeComponent implements OnInit {

  canvas: any;
  ctx: any;
  ctx1: any;
  url?: any;
  registerCount: number = 0;
  rowCount: number = 0;
  teacherRowCount: number = 0;
  distictRowCount: number = 0;
  regularPersentageCount: number = 0;
  notRegularPersentageCount: number = 0;
  pageEvent: PageEvent | undefined;
  labelData = ["distict1", "distict2", "distict3", "distict4", "distict5", "distict6"]
  displayedColumns: any = ["districtName", "teacherCount"]
  rowData: any[] = [];
  dataSource = new MatTableDataSource(this.rowData);
  pageSizeOptions: any = [5, 10, 20, 50, 100];
  pager: any = {};
  pageSize: number = 10;
  page: any = 0;
  myChart3: any;
  blockLabelList: any[] = [];
  blockDataList: any[] = [];
  clusterLabelList: any[] = [];
  schoolLabelList: any[] = [];
  schoolDataList: any[] = [];
  distictLabelList: any[] = [];
  distictDataList: any[] = [];
  clusterDataList: any[] = [];
  isDistict: boolean = false;
  isBlock: boolean = false;
  isCluster: boolean = false
  isSchool: boolean = false;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.getRegisterCount()
    this.getTotalTeacherCount()
    this.getDistictTeacherCount()
  }

  /**
  * Get Teacher Registation count
  */
  getRegisterCount() {
    this.url = '/v1/json/teachersCount';
    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.registerCount = res.result;
        }
      });
  }

  /**
  * Get Teacher attendance count
  */
  getTotalTeacherCount(): void {
    this.url = `/v1/json/teacher/findAll?pageNo=0&pageSize=10`;

    this.dataService.getAllData(this.url)
      .subscribe((res: any) => {
        if (res.code == 200) {
          this.teacherRowCount = res.result.totalElements;
          this.regularPersentageCount = (this.registerCount * 100) / this.teacherRowCount;
          this.notRegularPersentageCount = ((this.teacherRowCount - this.registerCount) * 100) / this.teacherRowCount;

          this.canvas = document.getElementById('myChart');

          this.ctx = this.canvas.getContext('2d');
          let myChart2 = new Chart(this.ctx, {
            type: 'pie',
            data: {
              labels: ["Regular", "Not Regular",],
              datasets: [{
                label: 'Teacher Attendance',
                data: [this.regularPersentageCount, this.notRegularPersentageCount],
                backgroundColor: ["blue", "red"],
                borderWidth: 0

              }
              ]
            },
            options: {
              legend: {
                display: true
              },
              responsive: true,
              maintainAspectRatio: false,
              scales: {
                yAxes: [
                  {
                    ticks: {
                      beginAtZero: true,
                    },
                  },
                ],
              },

            }
          });
        }
      })
  }

  /**
  * Get distict wise teacher count
  */
  getDistictTeacherCount() {
    this.distictLabelList = [];
    this.distictDataList = [];
    this.isDistict = true;
    this.isBlock = false;
    this.isCluster = false;
    this.isSchool = false;
    const data = [{
      "districtName": "2407-AHMEDABAD1",
      "teacherCount": 10
    },
    {
      "districtName": "2407-AHMEDABAD2",
      "teacherCount": 1
    },
    {
      "districtName": "2407-AHMEDABAD3",
      "teacherCount": 30
    },
    {
      "districtName": "2407-AHMEDABAD4",
      "teacherCount": 40
    }
    ]
    this.rowData = data;
    this.distictRowCount = 10;
    this.dataSource = new MatTableDataSource(this.rowData);
    data.forEach(element => {
      this.distictLabelList.push(element.districtName)
    });
    data.forEach(element => {
      this.distictDataList.push(element.teacherCount)
    });
    this.canvas = document.getElementById('teacherChart');

    this.ctx1 = this.canvas.getContext('2d');
    if (this.myChart3) {
      this.myChart3.destroy()
    }
    this.myChart3 = new Chart(this.canvas, {
      type: 'pie',
      data: {
        labels: this.distictLabelList,
        datasets: [{
          label: 'Teacher Attendence',
          data: this.distictDataList,
          backgroundColor: ["blue", "red", "green", "pink", "blue", "red", "green", "pink", "blue", "red", "green", "pink"],
          borderWidth: 0
        }]
      },
      options: {
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ],
        },


        onClick: (event: any, data: any) => {
          const point = data[0]['_index']
          const distictId = event.districtName
          const labelData = this.blockLabelList;
          this.getBlock(null, point)
        }
      }
    });
  }


  /**
  * display block chart
  */
  displayBlockChart(index: any) {
    this.blockLabelList = [];
    this.blockDataList = [];
    //using this code we call api
    const code = this.distictLabelList[0].split('-')[0]

    const data = [{
      "blockName": "2407-AHMEDABAD block",
      "teacherCount": 10
    },
    {
      "blockName": "2407-AHMEDABAD block",
      "teacherCount": 50
    },
    {
      "blockName": "2407-AHMEDABAD block",
      "teacherCount": 70
    }]
    data.forEach(element => {
      this.blockLabelList.push(element.blockName)
    });
    data.forEach(element => {
      this.blockDataList.push(element.teacherCount)
    });

    this.canvas = document.getElementById('teacherChart');

    this.ctx1 = this.canvas.getContext('2d');
    if (this.myChart3) {
      this.myChart3.destroy()
    }
    this.myChart3 = new Chart(this.canvas, {
      type: 'pie',
      data: {
        labels: this.blockLabelList,
        datasets: [{
          label: 'Teacher Attendence',
          data: this.blockDataList,
          backgroundColor: ["blue", "red", "green"],
          borderWidth: 0
        }]
      },
      options: {
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false,
        // display: true,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ],
        },
        onClick: (data: any, event: any) => {
          const point = event[0]['_index']
          const labelData = this.clusterLabelList;
          this.getCluster(null, point)
        }
      }
    });

  }


  /**
  * Get block wise teacher count
  */
  getBlock(event: any = null, point: number = 0) {

    // console.log("event", event.districtName, event)
    this.isDistict = false;
    this.isBlock = true;
    this.isCluster = false;
    this.isSchool = false;

    const distictName = event ? event.districtName : this.distictLabelList[point];
    //call api using below distictId
    const distictId = distictName.split("-")[0]

    this.rowData = [{
      "blockName": "2407-AHMEDABAD school",
      "teacherCount": 10
    },
    {
      "blockName": "2407-AHMEDABAD school",
      "teacherCount": 50
    },
    {
      "blockName": "2407-AHMEDABAD school",
      "teacherCount": 70
    }]
    this.dataSource = new MatTableDataSource(this.rowData);
    this.rowCount = 3
    this.isBlock = true;
    this.displayBlockChart(distictId)
  };

  /**
  * Get school wise teacher count
  */
  displaySchoolChart(index: any) {
    this.schoolLabelList = [];
    this.schoolDataList = [];
    const data = [{
      "schoolName": "2407-AHMEDABAD",
      "teacherCount": 90
    },
    {
      "schoolName": "2407-AHMEDABAD",
      "teacherCount": 10
    }]
    data.forEach(element => {
      this.schoolLabelList.push(element.schoolName)
    });
    data.forEach(element => {
      this.schoolDataList.push(element.teacherCount)
    })

    this.canvas = document.getElementById('teacherChart');

    this.ctx1 = this.canvas.getContext('2d');
    if (this.myChart3) {
      this.myChart3.destroy()
    }
    this.myChart3 = new Chart(this.canvas, {
      type: 'pie',
      data: {
        labels: this.schoolLabelList,
        datasets: [{
          label: 'Teacher Attendence',
          data: this.schoolDataList,
          backgroundColor: ["blue", "red"],
          borderWidth: 0
        }]
      },
      options: {
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ],
        },
      }
    });
  }

  /**
  * Get school wise teacher count
  */
  getSchool(event: any = null, point: number = 0) {
    this.isDistict = false;
    this.isBlock = false;
    this.isCluster = false;
    this.isSchool = true;

    const clusterName = event ? event.crcName : this.clusterLabelList[point];
    //call api using below clusterId
    const clusterId = clusterName.split("-")[0]

    this.rowData = [{
      "schoolName": "2407-AHMEDABAD",
      "teacherCount": 90
    },
    {
      "schoolName": "2407-AHMEDABAD",
      "teacherCount": 10
    }]
    this.dataSource = new MatTableDataSource(this.rowData);
    this.rowCount = 2
    this.isSchool = true;
    this.displaySchoolChart(clusterId)
  }

  /**
   * Get school wise cluster count
   */
  displayClusterChart(index: any) {
    this.clusterLabelList = [];
    this.clusterDataList = [];
    const data = [{ "crcName": "2407120001-ADARSH PRI.SCHOOL", "teacherCount": 10 },
    { "crcName": "2407120001-Bapunagar", "teacherCount": 10 }]
    data.forEach(element => {
      this.clusterLabelList.push(element.crcName)
    });
    data.forEach(element => {
      this.clusterDataList.push(element.teacherCount)
    })

    this.canvas = document.getElementById('teacherChart');

    this.ctx1 = this.canvas.getContext('2d');
    if (this.myChart3) {
      this.myChart3.destroy()
    }
    this.myChart3 = new Chart(this.canvas, {
      type: 'pie',
      data: {
        labels: this.clusterLabelList,
        datasets: [{
          label: 'Teacher Attendence',
          data: this.clusterDataList,
          backgroundColor: ["blue", "red", "blue", "red"],
          borderWidth: 0
        }]
      },
      options: {
        legend: {
          display: true
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
            },
          ],
        },
        onClick: (data: any, event: any) => {
          const point = event[0]['_index']
          const labelData = this.schoolLabelList;
          this.getSchool(null, point)
        }
      }
    });
  }

  getCluster(event: any = null, point: number = 0) {
    this.isDistict = false;
    this.isBlock = false;
    this.isCluster = true;
    this.isSchool = false;
    const blockName = event ? event.blockName : this.blockLabelList[point];
    //call api using below blockId
    const blockId = blockName.split("-")[0]
    this.rowData = [{ "crcName": "2407120001-ADARSH PRI.SCHOOL", "teacherCount": 10 }, { "crcName": "2407120001-Bapunagar", "teacherCount": 10 }]
    this.dataSource = new MatTableDataSource(this.rowData);
    this.rowCount = 2
    this.isCluster = true;
    this.displayClusterChart(blockId)
  }

  /**
  * @param event
  */
  pageNavigations(event: PageEvent) {
    this.page = event.pageIndex;
    this.pageSize = event.pageSize;
    //call api here
    // this.getMisReportList(this.page);
  }

  getPreviousDistict() {
    this.getDistictTeacherCount()
  }

  getPreviousBlock() {
    this.getBlock()
  }

  getPreviousCluster() {
    this.getCluster()
  }
}
