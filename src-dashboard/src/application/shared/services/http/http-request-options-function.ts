import {HttpRequestOptions} from "./http-request-options";

/**
 * Represents a function that transforms or augments an existing set of HTTP
 * request options. It takes the current `HttpRequestOptions` as input and
 * returns a modified set of options. This function is typically used to
 * dynamically adjust request options based on certain conditions or to add
 * additional headers, parameters, or other configuration to the request.
 *
 * @param current The current `HttpRequestOptions` to be transformed or
 * augmented.
 * @returns The modified `HttpRequestOptions` after applying the transformation.
 */
export type HttpRequestOptionsFunction =
  (current: HttpRequestOptions) =>
    HttpRequestOptions