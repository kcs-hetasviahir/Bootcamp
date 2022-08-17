import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as CryptoJS from 'crypto-js';
import { environment } from '../../environments/environment';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  usertype: string = "";
  baseUrl: string;
  proxybaseUrl: string;
  ppbaseUrl: string;

  constructor(private httpClient: HttpClient, private router: Router) {
    this.baseUrl = environment.API_URL;
    this.proxybaseUrl = "http://loclhost:9200/";
    this.ppbaseUrl = "http://loclhost:9200/";
  }

  createHeader(headers: HttpHeaders) {
    //let tokenParse = JSON.parse(localStorage.getItem('token'))   
    let tokenParse = localStorage.getItem('token')
    headers.set('Content-Type', 'application/json');
    headers.set('Authorization', `Bearer ${tokenParse}`);
    //headers.append('','');
  }

  /***********************
   *   get all records
   ***********************/

  trustedAgentgetAllData(url: any) {
    //let headers = new HttpHeaders();
    //this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');

    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`,
      'auth_string': localStorage.getItem('randomstr') || '',
      'Accept': 'application/json',
      'Cache-Control': 'no-cache, no-store, must-revalidate, post-check=0, pre-check=0',
      'Pragma': 'no-cache',
      'Expires': '0',
      'user-jwt-token': localStorage.getItem('user-jwt-token') || '',
      'user-account-id': localStorage.getItem('user-account-id') || '',
      'user-logged-in-key': 'true',
      'logged-user-id': localStorage.getItem('logged-user-id') || '',
    });
    return this.httpClient.get(this.ppbaseUrl + url, {
      headers: httpheaders
    });
  }
  getAllData(url: string) {
    //let headers = new HttpHeaders();
    //this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.get(this.baseUrl + url, {
      headers: httpheaders
    });
  }


  getPdf(url: string) {
    let tokenParse = localStorage.getItem('token');
    let headers = new HttpHeaders({
      'Content-Type': 'application/pdf',
      responseType: 'arraybuffer',
      Accept: 'application/pdf',
      observe: 'response',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.get(this.baseUrl + url, {
      headers: headers, responseType: 'arraybuffer'
    });
  }

  /***********************
   *   get all records
   ***********************/
  getDataById(url: string) {
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.get(this.baseUrl + url, {
      headers: httpheaders
    });
  }
  trustedAgentgetgetDataById(url: any) {
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`,
      'auth_string': localStorage.getItem('randomstr') || '',
      'Accept': 'application/json',
      'Cache-Control': 'no-cache, no-store, must-revalidate, post-check=0, pre-check=0',
      'Pragma': 'no-cache',
      'Expires': '0',
      'user-jwt-token': localStorage.getItem('user-jwt-token') || '',
      'user-account-id': localStorage.getItem('user-account-id') || '',
      'user-logged-in-key': 'true',
      'logged-user-id': localStorage.getItem('logged-user-id') || '',
    });
    return this.httpClient.get(this.ppbaseUrl + url, {
      headers: httpheaders
    });
  }
  /***********************
  *   post request
  ***********************/

  trustedAgentpostRequestWithObject(url: any, data: any) {

    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, data, {
      // headers: httpheaders
    });
  }
  agentpostRequestWithObject(url: string, data: any) {

    // let headers = new HttpHeaders();
    // this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.proxybaseUrl + url, data, {
      headers: httpheaders
    });
  }


  numberData(value: any) {
    // this.data.push(value)
    let data = value.toString();
    localStorage.setItem("scandata", data);
  }

  getScanValue() {
    document.addEventListener('scandata', (e) => {
      if (e) {
      }
    });
  }

  postRequestWithObject(url: string, data?: any) {
    // let headers = new HttpHeaders();
    // this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      // 'Content-Type': 'multipart/form-data',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, data, {
      headers: httpheaders
    });
  }

  postRequestWithFileObject(url: string, data: any) {

    // let headers = new HttpHeaders();
    // this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, data, {
      headers: httpheaders
    });
  }

  /*********************************
   *   post request without object
   *********************************/
  postRequestWithoutObject(url: any) {

    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, null, {
      headers: httpheaders
    });
  }

  /***********************
  *   put request 
  ***********************/

  trustedAgentputRequestWithObject(url: any, data: any) {
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`,
      'auth_string': localStorage.getItem('randomstr') || '',
      'Accept': 'application/json',
      'Cache-Control': 'no-cache, no-store, must-revalidate, post-check=0, pre-check=0',
      'Pragma': 'no-cache',
      'Expires': '0',
      'user-jwt-token': localStorage.getItem('user-jwt-token') || '',
      'user-account-id': localStorage.getItem('user-account-id') || '',
      'user-logged-in-key': 'true',
      'logged-user-id': localStorage.getItem('logged-user-id') || '',
    });
    data = JSON.stringify(data);
    return this.httpClient.put(this.ppbaseUrl + url, data, {
      headers: httpheaders
    });
  }
  putRequestWithObject(url: string, data: any) {
    // let headers = new HttpHeaders();
    // this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    data = JSON.stringify(data);
    return this.httpClient.put(this.baseUrl + url, data, {
      headers: httpheaders
    });
  }

  /***********************
  *   delete records
  ***********************/
  deleteRequest(url: string, id: number) {
    // let headers = new HttpHeaders();
    // this.createHeader(headers);
    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.delete(this.baseUrl + url + id, {
      headers: httpheaders
    });
  }

  /*****************************
   * upload sale rep csv file
   *****************************/
  uploadSalesRepCSV(url: string, data: any) {

    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, data, {
      headers: httpheaders
    });
  }

  partnerDetails(url: string, data: any) {

    let tokenParse = localStorage.getItem('token');
    const httpheaders = new HttpHeaders({
      'Authorization': `Bearer ${tokenParse}`
    })
    return this.httpClient.post(this.baseUrl + url, data, {
      headers: httpheaders
    });
  }

  // getCountryJsonContent(url: string) {
  //   return this.httpClient.get(url)
  //   .map((res:any) => res)

  // }

  getCountryJsonContent(url: string) {
    const httpheaders = new HttpHeaders({
      'Content-Type': 'application/json',
    })
    return this.httpClient.get(url, {
      headers: httpheaders
    });
  }


  encryptData(data: any) {
    return CryptoJS.AES.encrypt(JSON.stringify(data), 'mfcCMS').toString();
  }

  decryptData(data: string | CryptoJS.lib.CipherParams) {
    const bytes = CryptoJS.AES.decrypt(data, 'mfcCMS');
    if (bytes.toString()) {
      return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
    }
  }

  datetimeFormat(): string {
    return 'YYYY-MM-DD';
    //return 'YYYY-MM-DDT23:59:59+02:00';
  }

  convertDate(date: string | null): string {
    if (date !== null) {
      var datearray = date.split("-");
      return datearray[1] + "-" + datearray[0] + "-" + datearray[2];
    }
    return "";
  }

  public configObservable = new Subject<boolean>();
  emitConfig(val: boolean | undefined) {
    this.configObservable.next(val);
  }

  base64url(source: any) {
    let encodedSource = CryptoJS.enc.Base64.stringify(source);
    encodedSource = encodedSource.replace(/=+$/, '');
    encodedSource = encodedSource.replace(/\+/g, '-');
    encodedSource = encodedSource.replace(/\//g, '_');
    return encodedSource;
  }
  DecodeToken(objToken: string) {
    debugger;
    //objToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZiIsImF1ZCI6ImFjY291bnRzIiwiaWF0IjoxNTk3OTg5MzkwLCJuYmYiOjE1OTc5ODkyNzAsImV4cCI6MTU5Nzk5MTc5MCwic3ViIjoia2NzLnJhdmkyNzUiLCJpZCI6IjU3MTgwODUyMzE3Nzg3NjkyNzAiLCJ1c2VyX3R5cGUiOiJhY2NvdW50IiwiYWNjb3VudF9pZCI6IjkxMTE0ODEzMjAyMTExNzIzODMifQ";
    const base64Url = objToken.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  }

}
