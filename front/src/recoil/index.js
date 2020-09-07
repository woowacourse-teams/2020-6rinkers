import { atom } from "recoil";
import { TERMINOLOGY_ADMIN_PROTOTYPE, USER_PROTOTYPE } from "../constants";

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

export const terminologyAdminState = atom({
  key: "terminologyAdminState",
  default: TERMINOLOGY_ADMIN_PROTOTYPE,
  persistence_UNSTABLE: {
    type: "terminologyAdminState",
  },
});

export const terminologiesAdminState = atom({
  key: "terminologiesAdminState",
  default: [],
  persistence_UNSTABLE: {
    type: "terminologiesAdminState",
  },
});
