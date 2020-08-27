import React from "react";
import FavoriteIcon from "./FavoriteIcon";
import NotFavoriteIcon from "./NotFavoriteIcon";

const CocktailFavorite = ({ cocktail, cocktails, setCocktails }) => {
  return cocktail.favorite ? (
    <FavoriteIcon
      cocktailId={cocktail.id}
      cocktails={cocktails}
      setCocktails={setCocktails}
    />
  ) : (
    <NotFavoriteIcon
      cocktailId={cocktail.id}
      cocktails={cocktails}
      setCocktails={setCocktails}
    />
  );
};

export default CocktailFavorite;
