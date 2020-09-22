import React from "react";
import Alert from "react-s-alert";
import {
  deleteFavorite,
  fetchFavoriteCocktailIds,
  getCurrentUser,
} from "../../api";

const FavoriteIcon = ({ cocktailId, setFavorites }) => {
  const deleteFavoriteClick = async () => {
    try {
      await deleteFavorite(cocktailId);

      fetchFavoriteCocktailIds().then((response) => {
        setFavorites(response.data);
      });
    } catch (e) {
      Alert.error("즐겨찾기를 삭제하는데 실패했습니다.");
    }
  };

  return (
    <div onClick={deleteFavoriteClick}>
      <img src="/favorite.svg" style={{ width: 24 }} />
    </div>
  );
};

export default FavoriteIcon;
