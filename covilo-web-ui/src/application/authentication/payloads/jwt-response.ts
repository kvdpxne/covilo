export interface JwtResponse {
  token: string
  refreshedToken: string
  account: string
  email: string
  roles: string[]
}