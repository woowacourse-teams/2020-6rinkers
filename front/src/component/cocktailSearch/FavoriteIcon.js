import React from "react";
import { deleteFavorite } from "../../api";

const FavoriteIcon = ({ cocktailId }) => {
  const deleteFavoriteClick = () => {
    deleteFavorite(cocktailId);
  };

  return (
    <div onClick={deleteFavoriteClick}>
      <img src="/favorite.png" style={{ width: 30 }} />
    </div>
  );
};

export default FavoriteIcon;
