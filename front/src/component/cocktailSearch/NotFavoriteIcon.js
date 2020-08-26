import React from "react";
import { addFavorite } from "../../api";

const NotFavoriteIcon = ({ cocktailId, cocktails, setCocktails }) => {
  const addFavoriteClick = async () => {
    const favoriteRequest = {
      cocktailId: cocktailId,
    };
    await addFavorite(favoriteRequest);
    setCocktails(
      cocktails.map((cocktail) =>
        cocktail.id === cocktailId ? { ...cocktail, favorite: true } : cocktail
      )
    );
  };

  return (
    <div onClick={addFavoriteClick}>
      <img src="/not_favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default NotFavoriteIcon;
