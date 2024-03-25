import {HttpBridge} from "./http-bridge";
import {
  HttpClient, HttpErrorResponse,
  HttpHeaders
} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {HttpRequestOptions} from "./http-request-options";
import {HttpRequestOptionsFunction} from "./http-request-options-function";

export abstract class AbstractHttpBridge
  implements HttpBridge {

  private readonly httpClient: HttpClient;

  protected constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  private get defaultHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
      .append("Accept", "application/json")
      .append("Accept-Language", "en")
      .append("Age", "0")
      .append("Content-Type", "application/json")
      .append("Cache-Control", "no-cache")
      .append("Expires", "Sat, 01 Jan 2000 00:00:00 GMT");
  }

  public get defaultUrl(): string {
    return "";
  }

  public get defaultRequestOptions(): HttpRequestOptions {
    return {
      headers: this.defaultHttpHeaders,
      responseType: "json"
    }
  }

  /**
   * @see https://angular.io/guide/http#getting-error-details
   */
  private handleErrorResponse(
    error: HttpErrorResponse
  ): Observable<never> {
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
      error
    );
  }

  private handleResponse<T>(
    source: Observable<T>
  ): Observable<T> {
    return source.pipe<T>(
      catchError<T, Observable<never>>(this.handleErrorResponse)
    );
  }

  /**
   *
   */
  private configureHttpRequestOptions(
    optionsFunction?: HttpRequestOptionsFunction
  ): HttpRequestOptions  {
    return optionsFunction
      ? optionsFunction(this.defaultRequestOptions)
      : this.defaultRequestOptions
  }

  public delete<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.delete<T>(
        `${this.defaultUrl}/${url}`,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }

  public get<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.get<T>(
        `${this.defaultUrl}/${url}`,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }

  public patch<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.get<T>(
        `${this.defaultUrl}/${url}`,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }

  public post<T>(
    url: string,
    body: any | null,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.post<T>(
        `${this.defaultUrl}/${url}`,
        body,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }

  public put<T>(
    url: string,
    body: any | null,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.put<T>(
        `${this.defaultUrl}/${url}`,
        body,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }
}