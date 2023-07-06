import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiHttpClientService } from "../../shared"
import { Province } from "../index"

@Injectable({
  providedIn: "root"
})
export class LocationRegionService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private readonly api: ApiHttpClientService) {
    this.basePath = "location/region"
  }

  /**
   * @param p0 optional country key
   */
  public getAll(
    p0: string = ""
  ): Observable<Array<Province>> {
    let query = `${this.basePath}/all`
    // TODO make better idea to assigning parameters
    if (0 != p0.length) {
      const parameters = `?country=${p0}`
      query += parameters
    }
    return this.api.get(query)
  }
}
