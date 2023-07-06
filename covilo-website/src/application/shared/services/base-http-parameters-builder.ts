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
    console.log(source);
    console.log(typeof source)
    Object.keys(source).forEach((key: string) => {
      // @ts-ignore
      const value: any = source[key];
      if (undefined !== typeof(value) && null !== value) {
        parameters = parameters.append(key, value.toString());
      }
    })
    // source.forEach((key: string, value: any): void => {
    //   if (undefined !== typeof(value) && null !== value) {
    //     parameters = parameters.append(key, value.toString());
    //   }
    // });
    return parameters;
  }
}
