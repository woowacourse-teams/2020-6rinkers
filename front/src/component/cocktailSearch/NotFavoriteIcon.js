import React from "react";
import { addFavorite } from "../../api";

const NotFavoriteIcon = ({ cocktailId }) => {
  const addFavoriteClick = (e) => {
    const favoriteRequest = {
      cocktailId: cocktailId,
    };
    addFavorite(favoriteRequest);
  };

  return (
    <div onClick={addFavoriteClick}>
      <img src="/not_favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default NotFavoriteIcon;
