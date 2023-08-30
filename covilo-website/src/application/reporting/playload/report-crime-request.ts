import {City, User} from "../../core";

interface ReportCrimeRequest {

  place: City

  reporter?: User;

  confirmed: boolean
}