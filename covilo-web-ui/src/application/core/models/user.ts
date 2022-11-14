import { Account } from "../../authentication/models/account"

export interface User extends Account {
  identifier: string
  createdDate: Date
}