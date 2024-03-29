import { Injectable } from '@angular/core';
import { Subject, BehaviorSubject } from 'rxjs';

@Injectable()
export class LoaderService {

  public status: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  
  constructor() { }

  display(value: boolean) {
      this.status.next(value);
  }
  
}

