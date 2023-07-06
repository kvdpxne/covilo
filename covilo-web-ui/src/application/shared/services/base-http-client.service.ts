import { Injectable } from "@angular/core"
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders, HttpParams
} from "@angular/common/http"
import { catchError, Observable, retry, throwError } from "rxjs"
import { map } from "rxjs/operators"
import { BaseHttpParams } from "./base-http-params"
import {BaseHttpParametersBuilder} from "./base-http-parameters-builder";

@Injectable()
export abstract class BaseHttpClient {

  protected constructor(protected httpClient: HttpClient) {
  }

  abstract getUrl(): string;

  public get2<T>(path: string, parameters: any): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.get<T>(this.getUrl().concat(path), {
          headers: this.getHttpHeaders(),
          params: BaseHttpParametersBuilder.buildQueryParameters(parameters)
        }
      )
    )
  }

  public get<T>(
    url: string,
    showLoader: boolean = false,
    loadingContent: string | null = null
  ): Observable<T> {
    const response = this.httpClient.get<T>(this.getUrl() + url, {
      headers: this.getHttpHeaders(),
      params: new BaseHttpParams(showLoader, loadingContent)
    })
    return this.handleResponse<T>(response)
  }

  public post<T>(
    url: string,
    data: any,
    showLoader: boolean = false,
    loadingContent: string | null = null
  ): Observable<T> {
    const response = this.httpClient.post<T>(this.getUrl() + url, data, {
      headers: this.getHttpHeaders(),
      params: new BaseHttpParams(showLoader, loadingContent)
    })
    return this.handleResponse<T>(response)
  }

  public put<T>(
    url: string,
    data: any,
    showLoader: boolean = false,
    loadingContent: string | null = null
  ): Observable<T> {
    const response = this.httpClient.put<T>(this.getUrl() + url, data, {
      headers: this.getHttpHeaders(),
      params: new BaseHttpParams(showLoader, loadingContent)
    })
    return this.handleResponse<T>(response)
  }

  public delete<T>(
    url: string,
    showLoader: boolean = false,
    loadingContent: string | null = null
  ): Observable<T> {
    const response = this.httpClient.delete<T>(this.getUrl() + url, {
      headers: this.getHttpHeaders(),
      params: new BaseHttpParams(showLoader, loadingContent)
    })
    return this.handleResponse<T>(response)
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
    .append("Content-Type", "application/json")
    .append("Accept", "*/*")
    .append("Accept-Language", "en")
    .append("Cache-Control", "no-cache")
    .append("Expires", "Sat, 01 Jan 2000 00:00:00 GMT")
  }

  /**
   * @see https://angular.io/guide/http#getting-error-details
   */
  private handleError(error: HttpErrorResponse) {
    if (0 === error.status) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error("An error occurred:", error.error)
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
      new Error("Something bad happened; please try again later.")
    )
  }

  private handleResponse<T>(source: Observable<T>): Observable<T> {
    return source.pipe(
      retry({
        count: 3,
        delay: 1000
      }),
      catchError(this.handleError),
      map(response => {
        console.debug(response)
        return response
      })
    )
  }
}
