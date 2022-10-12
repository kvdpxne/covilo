import { UserProfile } from "./user-profile"

export interface User {
  identifier: string
  email: string
  password: string
  profile: UserProfile
  createdDate: Date
}