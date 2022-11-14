import { LoginCredentials } from "./login-credentials"

export interface Account extends LoginCredentials {

  birthDate: Date
  enabled: boolean
  roles: Array<string>
}