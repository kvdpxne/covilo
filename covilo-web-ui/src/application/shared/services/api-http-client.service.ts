import { Injectable } from '@angular/core'
import { HttpClient } from "@angular/common/http"
import { BaseHttpClient } from "./base-http-client.service"
import { environment } from "../../../environments/environment"

@Injectable({
  providedIn: 'root'
})
export class ApiHttpClientService extends BaseHttpClient {

  constructor(httpClient: HttpClient) {
    super(httpClient);
  }

  getUrl(): string {
    return environment.apiUrl
  }
}
