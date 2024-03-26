import {HttpBridge} from "./http-bridge";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {HttpRequestOptions} from "./http-request-options";
import {HttpRequestOptionsFunction} from "./http-request-options-function";
import {Injectable} from "@angular/core";

/**
 * Abstract class representing a bridge for making HTTP requests using
 * various HTTP methods.
 *
 * Provides a base implementation for common HTTP request operations.
 */
@Injectable()
export abstract class AbstractHttpBridge
  implements HttpBridge {

  /**
   * The instance of HttpClient used for making HTTP requests.
   */
  private readonly httpClient: HttpClient;

  /**
   * Constructs a new instance of the AbstractHttpBridge.
   *
   * @param httpClient The HttpClient service for making HTTP requests.
   */
  protected constructor(
    httpClient: HttpClient
  ) {
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

  public abstract get defaultUrl(): string

  public get defaultRequestOptions(): HttpRequestOptions {
    return {
      headers: this.defaultHttpHeaders,
      responseType: "json"
    };
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
   * Constructs the final URL by appending the provided path to the default
   * URL if necessary.
   *
   * If the provided path starts with a "/", it is appended directly to the
   * default URL; otherwise, it is appended after the default URL with a "/"
   * separator.
   *
   * @param path The path to be appended or used as is.
   * @returns The constructed URL.
   * @throws Error if the provided path is empty, undefined, or not valid.
   */
  private constructUrl(
    path: string
  ): string {
    // Check if the path is provided
    if (!path) {
      throw new Error("Path must be provided.");
    }

    // Get the default destination URL
    const destination: string = this.defaultUrl;

    // Check if the destination URL ends with "/"
    const isDestinationEndsWithSlash: boolean = destination.endsWith("/");

    // Check if the provided path starts with "/"
    const isPathStartsWithSlash: boolean = path.startsWith("/");

    if (isPathStartsWithSlash || isDestinationEndsWithSlash) {
      // If either the provided path is absolute or the destination URL ends
      // with "/", concatenate the URLs without adding an extra "/"
      return `${destination}${path}`;
    }

    if (isPathStartsWithSlash && isDestinationEndsWithSlash) {
      // If both the provided path and the destination URL end with "/",
      // remove the redundant "/" from the provided path
      return `${destination}${path.substring(1)}`;
    }

    // If none of the above conditions are met, concatenate the URLs with a
    // "/" separator
    return `${destination}/${path}`;
  }

  /**
   * Configures the HTTP request options by applying the provided options
   * function to the default request options.
   *
   * If an options function is provided, it is used to transform or augment
   * the default request options; otherwise, the default request options are
   * returned unchanged.
   *
   * @param optionsFunction Optional function to transform or augment the
   * default request options.
   * @returns The configured HTTP request options.
   */
  private configureHttpRequestOptions(
    optionsFunction?: HttpRequestOptionsFunction
  ): HttpRequestOptions {
    return optionsFunction
      ? optionsFunction(this.defaultRequestOptions)
      : this.defaultRequestOptions;
  }

  public delete<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T> {
    return this.handleResponse<T>(
      this.httpClient.delete<T>(
        this.constructUrl(url),
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
        this.constructUrl(url),
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
        this.constructUrl(url),
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
        this.constructUrl(url),
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
        this.constructUrl(url),
        body,
        this.configureHttpRequestOptions(optionsFunction)
      )
    );
  }
}