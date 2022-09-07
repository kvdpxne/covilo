import { Injectable } from '@angular/core'
import { Observable } from "rxjs";
import { ApiHttpClientService } from "../../shared/shared.module"
import { CrimeClassification } from "../core.module"

@Injectable({
  providedIn: "root"
})
export class CrimeClassificationService {

  // basic path of the rest controller rest
  private readonly basePath: string

  constructor(private httpClient: ApiHttpClientService) {
    this.basePath = "crime/classification"
  }

  public getAll(): Observable<Array<CrimeClassification>> {
    const query = `${this.basePath}/all`
    return this.httpClient.getV2(query)
  }
}
