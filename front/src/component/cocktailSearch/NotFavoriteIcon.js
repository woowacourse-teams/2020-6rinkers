import React from "react";
import {
  addFavorite,
  fetchFavoriteCocktailIds,
  getCurrentUser,
} from "../../api";
import Alert from "react-s-alert";

const NotFavoriteIcon = ({ cocktailId, setFavorites }) => {
  const addFavoriteClick = async () => {
    try {
      const favoriteRequest = {
        cocktailId,
      };
      await addFavorite(favoriteRequest);

      fetchFavoriteCocktailIds().then((response) => {
        setFavorites(response.data);
      });
    } catch (e) {
      Alert.error("즐겨찾기를 추가하는데 실패했습니다.");
    }
  };

  return (
    <div onClick={addFavoriteClick}>
      <img src="/not_favorite.svg" style={{ width: 20 }} />
    </div>
  );
};

export default NotFavoriteIcon;
