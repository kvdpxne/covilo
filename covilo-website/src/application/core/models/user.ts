export interface User {

  identifier: string;
  email: string;

  createdDate: Date;
  lastModifiedDate: Date;

  birthDate: Date
  enabled: boolean
  roles: Array<string>
}