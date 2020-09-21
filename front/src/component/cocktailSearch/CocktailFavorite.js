import React from "react";
import FavoriteIcon from "./FavoriteIcon";
import NotFavoriteIcon from "./NotFavoriteIcon";
import { useRecoilState } from "recoil";
import { favoriteState } from "../../recoil";

const CocktailFavorite = ({ cocktailId }) => {
  const [favorites, setFavorites] = useRecoilState(favoriteState);
  const favoriteCocktails = favorites.ids;

  return favoriteCocktails.includes(cocktailId) ? (
    <FavoriteIcon cocktailId={cocktailId} setFavorites={setFavorites} />
  ) : (
    <NotFavoriteIcon cocktailId={cocktailId} setFavorites={setFavorites} />
  );
};

export default CocktailFavorite;
