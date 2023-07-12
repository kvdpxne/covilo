import {Gender} from "./gender";
import {City} from "./city";

export interface User {
  identifier: string;
  recognizableName?: string;
  email: string;
  firstName: string;
  lastName: string;
  gender: Gender;
  birthDate: Date;
  livingPlace: City;
}