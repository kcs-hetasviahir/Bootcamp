import { HttpErrorResponse } from '@angular/common/http';
import { OnDestroy, OnInit, PLATFORM_ID, Injector, Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-base-page',
    template: ``
})
export class BasePageComponent implements OnInit, OnDestroy {
    public toastrService: ToastrService;
    public error = '';
    constructor(private injector: Injector) {
        this.toastrService = injector.get(ToastrService);
    }
    ngOnInit() {
    }

    ngOnDestroy() {
    }
    handleError(err: HttpErrorResponse) {
        let errList = '';
        if (err.error.errors) {
            this.error = err.error.errors;
            Object.keys(err.error.errors).forEach(function (key) {
                err.error.errors[key].forEach((e: any) => {
                    errList += e + '<br />';
                });
            });
        }
        if (errList == '') { errList = err.error.message; }
        this.toastrService.error(errList);
    }
}
