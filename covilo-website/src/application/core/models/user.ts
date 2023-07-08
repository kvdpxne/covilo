import {Gender} from "./gender";

export interface User {

  identifier: string;
  recognizableName: string;
  email: string;

  createdDate: Date;
  lastModifiedDate: Date;
  gender: Gender,
  birthDate: Date
  enabled: boolean
  roles: Array<string>
}