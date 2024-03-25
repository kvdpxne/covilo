import {HttpContext, HttpHeaders, HttpParams} from "@angular/common/http";

/**
 * Represents options that can be passed to an HTTP request in Angular.
 * These options provide flexibility and customization for making HTTP requests.
 */
export interface HttpRequestOptions {

  /**
   * Optional headers for the HTTP request.
   * Headers can be specified as HttpHeaders object or as an object with string keys
   * and string or string array values, representing header names and their respective values.
   * Example:
   * {
   *   'Content-Type': 'application/json',
   *   'Authorization': 'Bearer <token>'
   * }
   */
  headers?: HttpHeaders | {
    [header: string]: string | string[];
  };

  /**
   * Optional context for the HTTP request.
   * This can be used to provide additional context information for the request,
   * such as the current user session or environment details.
   */
  context?: HttpContext;

  /**
   * Specifies what should be observed in the response.
   * Currently, only observing the body of the response is supported.
   * This means that only the body of the response will be returned.
   */
  observe?: "body";

  /**
   * Optional parameters for the HTTP request.
   * Parameters can be specified as HttpParams object or as an object with string keys
   * and string, number, boolean, or array values, representing parameter names and their respective values.
   * Example:
   * {
   *   'page': 1,
   *   'pageSize': 10,
   *   'sort': ['name', 'asc']
   * }
   */
  params?: HttpParams | {
    [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>;
  };

  /**
   * Whether or not to report the progress of the HTTP request.
   * If set to true, progress events will be emitted to track the progress of the request.
   * This can be useful for displaying progress indicators to the user during long requests.
   */
  reportProgress?: boolean;

  /**
   * The expected response type of the HTTP request.
   * Currently, only JSON response type is supported.
   * This means that the response body will be parsed as JSON.
   */
  responseType?: "json";

  /**
   * Indicates whether cookies should be included in the request.
   * If set to true, cookies from the current domain will be included in the request headers.
   * This is necessary for certain authentication mechanisms that rely on cookies.
   */
  withCredentials?: boolean;
}
