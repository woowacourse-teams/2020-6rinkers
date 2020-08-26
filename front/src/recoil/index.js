import { atom } from "recoil";
import { USER_PROTOTYPE } from "../constants";

export const userState = atom({
  key: "userState",
  default: {
    authenticated: false,
    currentUser: USER_PROTOTYPE,
  },
  persistence_UNSTABLE: {
    type: "userState",
  },
});
