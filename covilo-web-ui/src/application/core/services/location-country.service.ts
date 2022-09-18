import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiHttpClientService } from "../../shared/shared.module"
import { LocationCountry } from "../core.module"

@Injectable({
  providedIn: "root"
})
export class LocationCountryService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private readonly api: ApiHttpClientService) {
    this.basePath = "location/country"
  }

  public getAll(): Observable<Array<LocationCountry>> {
    const query = `${this.basePath}/all`
    return this.api.get(query)
  }
}
