import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiHttpClientService } from "../../shared"
import { LocationCity } from "../index"

@Injectable({
  providedIn: "root"
})
export class LocationCityService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private readonly api: ApiHttpClientService) {
    this.basePath = "location/city"
  }

  /**
   * @param p0 optional country key
   * @param p1 optional region key
   */
  public getAll(
    p0: string = "",
    p1: string = ""
  ): Observable<Array<LocationCity>> {
    let query = `${this.basePath}/all`
    // TODO make better idea to assigning parameters
    if (0 != p0.length) {
      let parameters = `?country=${p0}`
      if (0 != p1.length) {
        parameters += `&region=${p1}`
      }
      query += parameters
    }
    return this.api.get(query)
  }

  /**
   * @param p0 required country key
   * @param p1 required region key
   * @param p2 required city key
   */
  public getOne(
    p0: string,
    p1: string,
    p2: string
  ): Observable<LocationCity> {
    if (0 == p0.length || 0 == p1.length || 0 == p2.length) {
      throw new Error("One of the required values is empty!")
    }
    let query = `${this.basePath}/one`
    const parameters = `?country=${p0}&region=${p1}&city=${p2}`
    query += parameters
    return this.api.get(query)
  }
}
