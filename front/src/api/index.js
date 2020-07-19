import axios from "axios";

const client = axios.create({});

export const fetchAllCocktails = () => client.get("/api/cocktails");
export const fetchCocktail = (id) => client.get(`/api/cocktails/${id}`);
export const createCocktail = (data) => client.post("/api/cocktails", data);
