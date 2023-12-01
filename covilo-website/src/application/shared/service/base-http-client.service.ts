import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, retry, throwError} from "rxjs";
import {map} from "rxjs/operators";
import {BaseHttpParametersBuilder} from "./base-http-parameters-builder";

@Injectable()
export abstract class BaseHttpClient {

  protected readonly httpClient: HttpClient;

  protected constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  abstract getUrl(): string;

  /**
   * The GET method requests a representation of the specified resource.
   * Requests using GET should only retrieve data.
   */
  public get<T>(
    path: string,
    parameters: any = {}
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.get<T>(this.getUrl().concat(path), {
        headers: this.getHttpHeaders(),
        params: BaseHttpParametersBuilder.buildQueryParameters(parameters)
      })
    );
  }

  /**
   * The POST method submits an entity to the specified resource, often
   * causing a change in state or side effects on the server.
   */
  public post<T>(
    path: string,
    body: any | null = {}
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.post<T>(this.getUrl().concat(path), body, {
        headers: this.getHttpHeaders()
      })
    );
  }

  /**
   * The PUT method replaces all current representations of the target resource
   * with the request payload.
   */
  public put<T>(
    path: string,
    body: any | null = null
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.put<T>(this.getUrl().concat(path), body, {
        headers: this.getHttpHeaders()
      })
    );
  }

  /**
   * The PATCH method applies partial modifications to a resource.
   */
  public patch<T>(
    path: string,
    body: any | null = null
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.patch<T>(this.getUrl().concat(path), body, {
        headers: this.getHttpHeaders()
      })
    );
  }

  /**
   * The DELETE method deletes the specified resource.
   */
  public delete<T>(
    path: string
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.delete<T>(this.getUrl().concat(path), {
        headers: this.getHttpHeaders()
      })
    );
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
      // .append("Content-Type", "application/json")
      .append("Accept", "*/*")
      .append("Accept-Language", "en")
      .append("Age", "0")
      .append("Cache-Control", "no-cache")
      .append("Expires", "Sat, 01 Jan 2000 00:00:00 GMT");
  }

  /**
   * @see https://angular.io/guide/http#getting-error-details
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    if (0 === error.status) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error("An error occurred:", error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // Return an observable with a user-facing error message.
    return throwError(() =>
      new Error("Something bad happened; please try again later.")
    );
  }

  private handleResponse<T>(source: Observable<T>): Observable<T> {
    return source.pipe<T, T>(
      retry<T>({
        count: 3,
        delay: 1000
      }),
      catchError<T, Observable<never>>(this.handleError)
    );
  }
}
