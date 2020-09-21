import axios from "axios";
import { ACCESS_TOKEN } from "../constants";

const client = axios.create({
  baseURL: `//${process.env.REACT_APP_HOST}`,
});

const config = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
  },
};

export const fetchAllCocktails = () => client.get("/api/cocktails");
export const fetchPagedCocktailsContainingWord = ({ contain, id, size }) =>
  client.get("/api/cocktails/contain-word", {
    params: { contain, id, size },
    headers: {
      Authorization: config.headers.Authorization,
    },
  });
export const fetchPagedCocktailsFilteredByTags = ({ tagIds, id, size }) =>
  client.get("api/cocktails/contain-tags", {
    params: { tagIds, id, size },
    headers: {
      Authorization: config.headers.Authorization,
    },
  });
export const fetchCocktail = (id) => client.get(`/api/cocktails/${id}`, config);
export const fetchTodayCocktail = () => client.get("/api/cocktails/today");
export const fetchCocktailsContaining = (contain) =>
  client.get(`api/cocktails/auto-complete`, { params: { contain } });
export const createCocktail = (data) =>
  client.post("/api/cocktails", data, config);
export const updateCocktail = (id, data) =>
  client.put(`/api/cocktails/${id}`, data, config);
export const deleteCocktail = (id) =>
  client.delete(`/api/cocktails/${id}`, config);
export const deleteAllCocktail = () => client.delete("/api/cocktails", config);

export const createTag = (data) => client.post("/api/tags", data, config);
export const updateTag = (id, data) =>
  client.put(`/api/tags/${id}`, data, config);
export const deleteTag = (id) => client.delete(`/api/tags/${id}`, config);
export const fetchAllTags = () => client.get("/api/tags");
export const fetchThreeRandomConceptTags = () =>
  client.get("/api/tags?tagType=CONCEPT&size=3&random=true");
export const fetchThreeRandomIngredientTags = () =>
  client.get("/api/tags?tagType=INGREDIENT&size=3&random=true");
export const fetchDislikeTags = () => client.get("/api/tags?tagType=DISLIKE");

export const createTerminology = (data) =>
  client.post("/api/terminologies", data, config);
export const fetchAllTerminologies = () => client.get("/api/terminologies");
export const fetchTerminology = (id) => client.get(`/api/terminologies/${id}`);
export const updateTerminology = (id, data) =>
  client.put(`/api/terminologies/${id}`, data, config);
export const deleteTerminology = (id) =>
  client.delete(`/api/terminologies/${id}`, config);

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
  return client.patch("/api/user/me", info, config);
};
export const withdraw = () => {
  client.delete("/api/user/me", config);
};
export const login = (loginRequest) =>
  client.post("/api/auth/login", loginRequest);
export const signup = (signupRequest) =>
  client.post("/api/auth/signup", signupRequest);

export const fetchMyFavorites = () => {
  client.get("/api/user/me/favorites", config);
};
export const fetchFavoriteCocktailIds = () => {
  return client.get("/api/user/me/favoriteIds", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};

export const addFavorite = (data) =>
  client.post("/api/user/me/favorites", data, config);
export const deleteFavorite = (cocktailId) =>
  client.delete(`/api/user/me/favorites?cocktailId=${cocktailId}`, config);
