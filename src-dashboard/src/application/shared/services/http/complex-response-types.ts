import {Observable} from "rxjs";
import {Page} from "../../../core";
import {ErrorMessage} from "./error-message";

export type RequestResponse<T> = Observable<Page<T> | T[] | ErrorMessage>