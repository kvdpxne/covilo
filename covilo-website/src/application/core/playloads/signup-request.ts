export interface SignupRequest {

  birthDate: Date;

  firstName: string;

  lastName: string;

  email: string;

  password: string;

  confirmPassword: string;

  privacyPolicy: boolean;
}