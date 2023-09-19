import {City, User} from "../index";
import {Category} from "../model/category";

export type ReportCrimeRequest = {
  title: string;
  description: string;
  categories: Category[];
  time: Date;
  reporter?: User;
  place: City;
  confirmed: boolean;
}