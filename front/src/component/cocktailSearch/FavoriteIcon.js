import React from "react";
import { deleteFavorite } from "../../api";

const FavoriteIcon = ({ cocktailId, cocktails, setCocktails }) => {
  const deleteFavoriteClick = async () => {
    await deleteFavorite(cocktailId);

    setCocktails(
      cocktails.map((cocktail) =>
        cocktail.id === cocktailId ? { ...cocktail, favorite: false } : cocktail
      )
    );
  };

  return (
    <div onClick={deleteFavoriteClick}>
      <img src="/favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default FavoriteIcon;
