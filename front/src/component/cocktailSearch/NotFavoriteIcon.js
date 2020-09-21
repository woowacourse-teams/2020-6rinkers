import React from "react";
import {
  addFavorite,
  fetchFavoriteCocktailIds,
  getCurrentUser,
} from "../../api";

const NotFavoriteIcon = ({ cocktailId, setFavorites }) => {
  const addFavoriteClick = async () => {
    const favoriteRequest = {
      cocktailId: cocktailId,
    };
    await addFavorite(favoriteRequest);

    fetchFavoriteCocktailIds().then((response) => {
      setFavorites(response.data);
    });
  };

  return (
    <div onClick={addFavoriteClick}>
      <img src="/not_favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default NotFavoriteIcon;
