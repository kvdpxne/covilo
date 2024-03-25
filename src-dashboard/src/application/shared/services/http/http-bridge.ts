import {Observable} from "rxjs";
import {HttpRequestOptionsFunction} from "./http-request-options-function";
import {HttpRequestOptions} from "./http-request-options";

/**
 * Represents a bridge for making HTTP requests using various HTTP methods
 * such as GET, POST, PUT, PATCH, and DELETE.
 *
 * This interface defines methods for each HTTP method along with options
 * that can be provided to customize the request.
 */
export interface HttpBridge {

  get defaultUrl(): string;

  get defaultRequestOptions(): HttpRequestOptions;

  /**
   * Makes an HTTP DELETE request to the specified URL.
   *
   * @param url The URL to which the DELETE request should be made.
   * @param optionsFunction Optional function to transform or augment the HTTP
   * request options.
   * @returns An observable that emits the response from the DELETE request.
   */
  delete<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T>;

  /**
   *
   */
  get<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T>;

  /**
   *
   */
  patch<T>(
    url: string,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T>;

  /**
   *
   */
  post<T>(
    url: string,
    body: any | null,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T>;

  /**
   *
   */
  put<T>(
    url: string,
    body: any | null,
    optionsFunction?: HttpRequestOptionsFunction
  ): Observable<T>;
}