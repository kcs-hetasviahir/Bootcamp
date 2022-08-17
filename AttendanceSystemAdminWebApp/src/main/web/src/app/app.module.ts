import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgHttpLoaderModule } from 'ng-http-loader';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HttpConfigInterceptor } from './interceptor/http.confing';
import { SharedModule } from './shared/shared.module';
import { NgxPermissionsModule } from 'ngx-permissions';
import { MaterialModule } from 'src/app/shared/material.module';
import { AgGridModule } from 'ag-grid-angular';
import { CustomTooltip } from 'src/app/services/custom-tooltip.component'
import { CustomLoaderComponent } from './components/shared/custom-loader/custom-loader.component'
import { MatPaginatorModule } from '@angular/material/paginator';
@NgModule({
  declarations: [
    AppComponent,
    CustomLoaderComponent,
  ],
  imports: [
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    NgHttpLoaderModule.forRoot(),
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot({ timeOut: 3000, closeButton: true, enableHtml: true, positionClass: 'toast-top-right', preventDuplicates: true, progressBar: true }),
    NgxPermissionsModule.forRoot(),
    MaterialModule,
    AgGridModule.withComponents([CustomTooltip])
  ],
  exports: [
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpConfigInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    CustomLoaderComponent
  ]
})
export class AppModule { }
