import { atom } from "recoil";
import {
  INGREDIENT_ADMIN_PROTOTYPE,
  TERMINOLOGY_ADMIN_PROTOTYPE,
  USER_COCKTAIL_PROTOTYPE,
  USER_PROTOTYPE,
} from "../constants";

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

export const favoriteState = atom({
  key: "favoriteState",
  default: {
    ids: [],
  },
  persistence_UNSTABLE: {
    type: "favoriteState",
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

export const ingredientAdminState = atom({
  key: "ingredientAdminState",
  default: INGREDIENT_ADMIN_PROTOTYPE,
  persistence_UNSTABLE: {
    type: "ingredientAdminState",
  },
});

export const ingredientsAdminState = atom({
  key: "ingredientsAdminState",
  default: [],
  persistence_UNSTABLE: {
    type: "ingredientsAdminState",
  },
});

export const userCocktailState = atom({
  key: "userCocktailState",
  default: USER_COCKTAIL_PROTOTYPE,
  persist_UNSTABLE: {
    type: "userCocktailState",
  },
});
