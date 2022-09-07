import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseHttpParams } from './base-http-params';

@Injectable()
export abstract class BaseHttpClient {

  abstract getUrl(): string;

  protected constructor(protected httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  /**
   * @deprecated use getV2
   */
  public get<T>(url: string | null = null,
                showLoader: boolean = false,
                loadingContent: string | null = null
  ): Observable<T> {
    const headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Accept', '*/*')
    .append('Cache-Control', 'no-cache')
    .append('Pragma', 'no-cache')
    .append('Expires', 'Sat, 01 Jan 2000 00:00:00 GMT');

    return this.httpClient.get<T>(this.getUrl() + url, {
      headers: headers,
      params: new BaseHttpParams(showLoader, loadingContent)
    })
    .pipe(
      map((response: T) => {
        return response;
      })
    )
  }

  /**
   * @see https://angular.io/guide/http#getting-error-details
   */
  private handleError(error: HttpErrorResponse) {
    if (0 === error.status) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error)
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      )
    }
    // Return an observable with a user-facing error message.
    return throwError(() =>
      new Error('Something bad happened; please try again later.')
    )
  }

  public getV2<T>(url: string | null = null,
                  showLoader: boolean = false,
                  loadingContent: string | null = null
  ): Observable<T> {
    const headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Accept', '*/*')
    .append('Cache-Control', 'no-cache')
    .append('Pragma', 'no-cache')
    .append('Expires', 'Sat, 01 Jan 2000 00:00:00 GMT');

    return this.httpClient.get<T>(this.getUrl() + url, {
      headers: headers
    }).pipe(
      retry(3),
      catchError(this.handleError),
      map(response => {
        console.debug(response)
        return response
      })
    )
  }
}
