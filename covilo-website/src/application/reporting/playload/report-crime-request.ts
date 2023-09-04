import {City, User} from "../../core";
import {Category} from "../../core/models/category";

export interface ReportCrimeRequest {

  datetime: Date;

  city: City;

  reporter: User | null;

  category: Category;

  description?: string;

  confirmed: boolean;
}