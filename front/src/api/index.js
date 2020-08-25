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
export const fetchPagedCocktails = ({ contain, id, size }) =>
  client.get("/api/cocktails/pages", {
    params: { contain, id, size },
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const fetchCocktail = (id) => client.get(`/api/cocktails/${id}`);
export const fetchTodayCocktail = () => client.get("/api/cocktails/today");
export const fetchCocktailsContaining = (contain) =>
  client.get(`api/cocktails/auto-complete`, { params: { contain } });
export const createCocktail = (data) => client.post("/api/cocktails", data);
export const updateCocktail = (id, data) =>
  client.put(`/api/cocktails/${id}`, data);
export const deleteCocktail = (id) => client.delete(`/api/cocktails/${id}`);
export const deleteAllCocktail = () => client.delete("/api/cocktails");

export const createTag = (data) => client.post("/api/tags", data);
export const deleteTag = (id) => client.delete(`/api/tags/${id}`);
export const fetchAllTags = () => client.get("/api/tags");

export const createRecommend = (recommend) =>
  client.post(`/api/cocktails/recommend`, recommend);

export const createCocktailsByCsv = (file) =>
  client.post("/api/cocktails/upload/csv", file, {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
export const createTagsByCsv = (file) =>
  client.post("/api/tags/upload/csv", file, {
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });

export const addFavorite = (data) => {
  client.post("/api/user/me/favorites", data, config);
};
export const deleteFavorite = (id) => {
  client.delete(`/api/user/me/favorites/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  });
};
