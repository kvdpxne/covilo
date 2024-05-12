import {User} from "../_models/user";
import {get} from "../../_shared/_services/http-bridge";

const me = async (): Promise<User> => {
  return await get("api/v1/me")
}

const updatePassword = async () => {

}

export {
  me
}