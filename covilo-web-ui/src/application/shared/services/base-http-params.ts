import { HttpParams } from "@angular/common/http";

export class BaseHttpParams extends HttpParams {

  constructor(
    public showLoader: boolean,
    public loadingContent: string | null) {
    super();
  }
}
