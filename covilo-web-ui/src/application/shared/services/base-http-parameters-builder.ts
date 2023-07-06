import {HttpParams} from "@angular/common/http";

/**
 *
 */
export class BaseHttpParametersBuilder {

  /**
   *
   */
  public static buildQueryParameters(source: [parameter: any]): HttpParams {
    let parameters: HttpParams = new HttpParams();
    source.forEach((key: string, value: any): void => {
      if (undefined !== typeof(value) && null !== value) {
        parameters = parameters.append(key, value.toString());
      }
    });
    return parameters;
  }
}
