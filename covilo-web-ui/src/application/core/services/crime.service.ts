import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { ApiHttpClientService } from "../../shared/shared.module";
import { Crime } from "../core.module";

@Injectable({
  providedIn: 'root'
})
export class CrimeService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private httpClient: ApiHttpClientService) {
    this.basePath = "crime"
  }

  /**
   * @param p0 optional country key
   * @param p1 optional region key
   * @param p2 optional city key
   */
  public getAll(
    p0: string = "",
    p1: string = "",
    p2: string = ""
  ): Observable<Array<Crime>> {
    let query = `${this.basePath}/all`

    // TODO make better idea to assigning parameters
    if (0 != p0.length) {
      let parameters = `?country=${p0}`
      if (0 != p1.length) {
        parameters += `&region=${p1}`
        if (0 != p2.length) {
          parameters += `&city=${p2}`
        }
      }
      query += parameters
    }

    return this.httpClient.getV2(query)
  }
}
