import { Injectable, EventEmitter } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import * as CryptoJS from 'crypto-js';
// import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CommonService {
  constructor(private _http: HttpClient) {
  }

  public get(url: any) {
    return this._http.get(environment.API_URL + url);
  }

  public post(url: any, data: any, headers: any = {}) {
    return this._http.post(url, data, { headers: headers });
  }

  public put(url: any, data: any) {
    return this._http.put(environment.API_URL + url, data);
  }

  public patch(url: any, data: any, headers: any = {}) {
    return this._http.patch(environment.API_URL + url, data, { headers: headers });
  }

  public delete(url: any) {
    return this._http.delete(environment.API_URL + url);
  }

  encryptData(data: any) {
    return CryptoJS.AES.encrypt(JSON.stringify(data), 'mfcCMS').toString();
  }

  decryptData(data: any) {
    const bytes = CryptoJS.AES.decrypt(data, 'mfcCMS');
    if (bytes.toString()) {
      return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
    }
  }
  datetimeFormat(): string {
    return 'YYYY-MM-DD';
    //return 'YYYY-MM-DDT23:59:59+02:00';
  }

  convertDate(date: string): string | null {
    if (date !== null) {
      var datearray = date.split("-");
      return datearray[1] + "-" + datearray[0] + "-" + datearray[2];
    }
    return null;
  }
}
