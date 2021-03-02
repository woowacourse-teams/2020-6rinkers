import axios from "axios";
import { ACCESS_TOKEN } from "../constants";

const client = axios.create({
  baseURL: `//${process.env.REACT_APP_HOST}`,
});

export const fetchAllCocktails = () => client.get("/api/cocktails");
export const fetchPagedCocktailsContainingWord = ({ contain, id, size }) =>
  client.get("/api/cocktails/contain-word", {
    params: { contain, id, size },
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchPagedCocktailsFilteredByTags = ({ tagIds, id, size }) =>
  client.get("api/cocktails/contain-tags", {
    params: { tagIds, id, size },
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchCocktail = (id) =>
  client.get(`/api/cocktails/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchTodayCocktail = () => client.get("/api/cocktails/today");
export const fetchCocktailsContaining = (contain) =>
  client.get(`api/cocktails/auto-complete`, { params: { contain } });
export const createCocktail = (data) =>
  client.post("/api/cocktails", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const updateCocktail = (id, data) =>
  client.put(`/api/cocktails/${id}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const deleteCocktail = (id) =>
  client.delete(`/api/cocktails/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const deleteAllCocktail = () =>
  client.delete("/api/cocktails", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });

export const createTag = (data) =>
  client.post("/api/tags", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const updateTag = (id, data) =>
  client.put(`/api/tags/${id}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const deleteTag = (id) =>
  client.delete(`/api/tags/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchAllTags = () => client.get("/api/tags");
export const fetchThreeRandomConceptTags = () =>
  client.get("/api/tags?tagType=CONCEPT&size=3&random=true");
export const fetchThreeRandomIngredientTags = () =>
  client.get("/api/tags?tagType=INGREDIENT&size=3&random=true");
export const fetchDislikeTags = () => client.get("/api/tags?tagType=DISLIKE");

export const createTerminology = (data) =>
  client.post("/api/terminologies", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchAllTerminologies = () => client.get("/api/terminologies");
export const fetchTerminology = (id) => client.get(`/api/terminologies/${id}`);
export const updateTerminology = (id, data) =>
  client.put(`/api/terminologies/${id}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const deleteTerminology = (id) =>
  client.delete(`/api/terminologies/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const createIngredient = (data) => {
  client.post("/api/ingredients", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
export const fetchAllIngredients = () =>
  client.get("/api/ingredients", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchIngredient = (id) =>
  client.get(`/api/ingredients/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const updateIngredient = (id, data) => {
  client.put(`/api/ingredients/${id}`, data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
export const deleteIngredient = (id) => {
  client.delete(`/api/ingredients/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};

export const createRecommend = (recommend) =>
  client.post(`/api/cocktails/recommend`, recommend);

export const createResourceByCsv = (resourceName, file) =>
  client.post(`/api/${resourceName}/upload/csv`, file, {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });

export const getCurrentUser = () => {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return client.get("/api/user/me", {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};

export const updateUser = (info) => {
  return client.patch("/api/user/me", info, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
export const withdraw = () => {
  client.delete("/api/user/me", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
export const login = (loginRequest) =>
  client.post("/api/user/login", loginRequest);
export const signup = (signupRequest) =>
  client.post("/api/user/signup", signupRequest);

export const fetchMyFavorites = () => {
  client.get("/api/user/me/favorites", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
export const fetchFavoriteCocktailIds = () => {
  return client.get("/api/user/me/favoriteIds", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};

export const addFavorite = (data) =>
  client.post("/api/user/me/favorites", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const deleteFavorite = (cocktailId) =>
  client.delete(`/api/user/me/favorites?cocktailId=${cocktailId}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });

export const createUserCocktail = (data) => {
  client.post("/api/user-cocktails", data, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};

export const fetchAllUserCocktails = () => {
  client.get("/api/user-cocktails", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
