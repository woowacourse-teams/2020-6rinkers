import React from "react";
import {
  deleteFavorite,
  fetchFavoriteCocktailIds,
  getCurrentUser,
} from "../../api";

const FavoriteIcon = ({ cocktailId, setFavorites }) => {
  const deleteFavoriteClick = async () => {
    await deleteFavorite(cocktailId);

    fetchFavoriteCocktailIds().then((response) => {
      setFavorites(response.data);
    });
  };

  return (
    <div onClick={deleteFavoriteClick}>
      <img src="/favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default FavoriteIcon;
