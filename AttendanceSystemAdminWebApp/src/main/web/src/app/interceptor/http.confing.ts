import { Injectable } from "@angular/core";
import { map, catchError } from "rxjs/operators";

import { apiLoader } from '../constants/global.constants';
import {
	HttpRequest,
	HttpHandler,
	HttpEvent,
	HttpInterceptor,
	HttpResponse,
	HttpErrorResponse
} from "@angular/common/http";
import { throwError, fromEvent, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { CommonService } from '../services/common.service';

@Injectable()
export class HttpConfigInterceptor implements HttpInterceptor {
	onlineEvent: Observable<Event>;
	offlineEvent: Observable<Event>;
	constructor(
		private _router: Router,
		private _commonService: CommonService) {
		this.onlineEvent = fromEvent(window, 'online');
		this.offlineEvent = fromEvent(window, 'offline');

	}
	intercept(
		request: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {

		const token: string | null = localStorage.getItem('token');


		if (token) {
			request = request.clone({ headers: request.headers.set('Authorization', `Bearer ${token}`) });
		}
		return next.handle(request).pipe(map((event: HttpEvent<any>) => {
			if (event instanceof HttpResponse) {
				//this.service.hideloader();
				if (event.status == 200 && event.url == environment.API_URL + 'cms/uploadcmsimage') {
					event = event.clone({ body: { "imageUrl": event.body.data } })
				}
				if (apiLoader) {

				}
			}
			return event;
		}), catchError((error: HttpErrorResponse) => {

			if (error.status == 401) {
				//this._toastrService.error(error.error.message)
				localStorage.clear();
				this._router.navigate(['/']);
				/*setTimeout(() => {
					//this._authService.logout();
				}, 5000);  //5s */
			}
			if (error.status == 429) {
				this._router.navigate(['/mis-report']);
			}
			if (error.status >= 500 && error.url != environment.API_URL + 'reportError') {
				let data = {
					"title": 'Angular Error occured at ' + error.url,
					"description": JSON.stringify(error)
				};
				this._commonService.post('reportError', data).subscribe((res: any) => { })
			}
			if (apiLoader) {

			}
			return throwError(error);
		}))
	}

}