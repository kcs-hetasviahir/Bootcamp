
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';
import { MisReportComponent } from './pages/MisReport/view-mis-report/mis-report.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ResortsetupaddeditComponent } from './pages/resortsetupaddedit/resortsetupaddedit.component';
import { ResortsetuplistComponent } from './pages/resortsetuplist/resortsetuplist.component';
import { AddEditTeacherComponent } from './pages/Teacher/add-edit-teacher/add-edit-teacher.component';
import { ViewTeacherComponent } from './pages/Teacher/view-teacher/view-teacher.component';
import { DashboardHomeComponent } from './pages/Dashboard/dashboard-home/dashboard-home.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'dashboard', component: DashboardHomeComponent },
      { path: 'mis-report', component: MisReportComponent },
      { path: 'user-management', component: ResortsetuplistComponent },
      { path: 'view-teacher', component: ViewTeacherComponent },
      { path: 'add-edit-teacher', component: AddEditTeacherComponent },
      { path: 'add-edit-teacher/:id', component: AddEditTeacherComponent },
      { path: 'resort-add-edit', component: ResortsetupaddeditComponent },
      { path: 'resort-add-edit/:resortId', component: ResortsetupaddeditComponent },
      // { path: '**', component: AdminDashboardComponent },

    ]

  },
]
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule, ReactiveFormsModule]
})
export class LayoutRoutingModule { }
