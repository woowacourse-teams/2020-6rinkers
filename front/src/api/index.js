import axios from "axios";

const client = axios.create({});

export const fetchAllCocktails = () => client.get("/api/cocktails");
export const fetchCocktail = (id) => client.get(`/api/cocktails/${id}`);
export const fetchTodayCocktail = () => client.get("/api/cocktails/today");
export const createCocktail = (data) => client.post("/api/cocktails", data);
export const createRecommend = (recommend) =>
  client.post(`/api/cocktails/recommend`, recommend);
export const createCocktailsByCsv = (file) =>
  client.post("/api/cocktails/upload/csv", file, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
export const createTagsByCsv = (file) =>
  client.post("/api/tags/upload/csv", file, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
