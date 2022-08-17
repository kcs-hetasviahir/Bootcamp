import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPermissionsModule } from 'ngx-permissions';
import { MaterialModule } from 'src/app/shared/material.module';
import { MatRadioModule } from '@angular/material/radio';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FooterComponent } from '../shared/footer/footer.component';
import { HeaderComponent } from '../shared/header/header.component';
import { LayoutComponent } from './layout.component';
import { LayoutRoutingModule } from './layout.routing.module';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AgGridModule } from 'ag-grid-angular';
import { CustomTooltip } from 'src/app/services/custom-tooltip.component';
import { MisReportComponent } from './pages/MisReport/view-mis-report/mis-report.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import {
  NgxMatDatetimePickerModule,
  NgxMatNativeDateModule,
  NgxMatTimepickerModule
} from '@angular-material-components/datetime-picker';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { EmailSendComponent } from './pages/MisReport/email-send/email-send.component';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { MatSortModule } from '@angular/material/sort';
import { NgxPrintModule } from 'ngx-print';
import { TimePipe } from 'src/app/pipes/time.pipe';
import { ResortsetupaddeditComponent } from './pages/resortsetupaddedit/resortsetupaddedit.component';
import { ResortsetuplistComponent } from './pages/resortsetuplist/resortsetuplist.component';
import { ViewTeacherComponent } from './pages/Teacher/view-teacher/view-teacher.component';
import { AddEditTeacherComponent } from './pages/Teacher/add-edit-teacher/add-edit-teacher.component';
import { DashboardHomeComponent } from './pages/Dashboard/dashboard-home/dashboard-home.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    LayoutComponent,
    MisReportComponent,
    EmailSendComponent,
    ViewTeacherComponent,
    AddEditTeacherComponent,
    ResortsetupaddeditComponent,
    ResortsetuplistComponent,
    DashboardHomeComponent,
    TimePipe,
    CustomTooltip
  ],
  imports: [
    CommonModule,
    LayoutRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    MatCheckboxModule,
    MatRadioModule,
    MatToolbarModule,
    MatDatepickerModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    NgxDropzoneModule,
    NgxMatDatetimePickerModule,
    NgxMatTimepickerModule,
    NgxMatNativeDateModule,
    NgxPermissionsModule.forChild(),
    BsDropdownModule.forRoot(),
    AgGridModule.withComponents([CustomTooltip]),
    NgxPrintModule,
    MatCardModule,
  ],
  exports: [
    MatPaginatorModule, TimePipe],
  providers: [
  ]
})
export class LayoutModule { }