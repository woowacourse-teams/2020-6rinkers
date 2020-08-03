import axios from "axios";

const client = axios.create({
  baseURL: "http://13.209.150.130:8080",
});

export const fetchAllCocktails = () => client.get("/api/cocktails");
export const fetchPagedCocktails = (id, size) =>
  client.get("/api/cocktails/pages", { params: { id, size } });
export const fetchCocktail = (id) => client.get(`/api/cocktails/${id}`);
export const fetchTodayCocktail = () => client.get("/api/cocktails/today");
export const createCocktail = (data) => client.post("/api/cocktails", data);
export const createTag = (data) => client.post("/api/tags", data);
export const fetchAllTags = () => client.get("/api/tags");
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
