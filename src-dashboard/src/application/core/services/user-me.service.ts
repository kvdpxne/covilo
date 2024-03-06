import {Injectable} from "@angular/core";
import {ApiHttpClientService} from "../../shared";
import {Observable} from "rxjs";
import {User} from "../models/user";

/**
 * Default path for user-related API endpoints.
 *
 * This path serves as the base URL for sending requests related to user
 * data retrieval. It is used to construct the endpoint for fetching
 * information about the current authenticated user.
 */
const DEFAULT_PATH: string = "api/v1/me";

/**
 * Service responsible for handling user-related operations.
 *
 * This service provides methods for fetching user data, specifically
 * information about the currently authenticated user. It encapsulates
 * functionality related to making HTTP requests to the backend API for
 * retrieving user data.
 */
@Injectable({
  providedIn: "root"
})
export class UserMeService {

  /**
   * Constructor to initialize the UserMeService.
   *
   * @param httpClientService The HTTP client service used for
   *                          making API requests.
   */
  public constructor(
    private readonly httpClientService: ApiHttpClientService
  ) {

  }

  /**
   * Sends a request to the backend API to fetch information about the
   * current authenticated user.
   *
   * This method constructs the API endpoint and sends a GET request to the
   * backend.
   *
   * @returns An Observable that emits a {@link User} object when the request
   *          is successful.
   */
  public requestMe(): Observable<User> {
    // Constructs the API endpoint for fetching user data
    const path = `${DEFAULT_PATH}/me`;

    return this.httpClientService.get(path);
  }
}
