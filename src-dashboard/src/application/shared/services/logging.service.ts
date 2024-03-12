import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoggingService {

  public isLoggable(): boolean {
    return !environment.production;
  }

  public trace(message: () => string): void {
    if (this.isLoggable()) {
      console.trace(message())
    }
  }

  public debug(message: () => string): void {
    if (this.isLoggable()) {
      console.debug(message());
    }
  }
}
