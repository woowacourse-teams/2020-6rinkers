import React from "react";
import FavoriteIcon from "./FavoriteIcon";
import NotFavoriteIcon from "./NotFavoriteIcon";

const CocktailFavorite = ({ cocktail }) => {
  return cocktail.favorite ? (
    <FavoriteIcon cocktailId={cocktail.id} />
  ) : (
    <NotFavoriteIcon cocktailId={cocktail.id} />
  );
};

export default CocktailFavorite;
 