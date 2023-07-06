import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiHttpClientService } from "../../shared"
import { Country } from "../index"

@Injectable({
  providedIn: "root"
})
export class LocationCountryService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private readonly api: ApiHttpClientService) {
    this.basePath = "location/country"
  }

  public getAll(): Observable<Array<Country>> {
    const query = `${this.basePath}/all`
    return this.api.get(query)
  }
}
