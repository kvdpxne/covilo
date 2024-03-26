import {AbstractHttpBridge} from "./abstract-http-bridge";
import {Injectable} from "@angular/core";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";

/**
 * A concrete implementation of the AbstractHttpBridge class that uses the
 * environment-specific API URL.
 *
 * This service is provided at the root level of the Angular application.
 */
@Injectable({
  providedIn: "root"
})
export class ApiHttpBridge
  extends AbstractHttpBridge {

  /**
   * Constructs a new instance of the ApiHttpBridge.
   *
   * @param httpClient The HttpClient service for making HTTP requests.
   */
  public constructor(
    httpClient: HttpClient
  ) {
    super(httpClient);
  }

  /**
   * Overrides the defaultUrl property from the AbstractHttpBridge class.
   *
   * @returns The environment-specific API URL defined in the environment
   * configuration.
   */
  public override get defaultUrl(): string {
    return environment.apiUrl;
  }
}