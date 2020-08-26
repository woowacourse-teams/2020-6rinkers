import React from "react";
import { Link } from "react-router-dom";
import { deleteFavorite } from "../../api";
import Alert from "react-s-alert";

const MyFavorite = ({ cocktail, loadCurrentlyLoggedInUser }) => {
  const onDeleteFavorite = (e) => {
    e.preventDefault();
    e.stopPropagation();
    deleteFavorite(cocktail.id)
      .then(loadCurrentlyLoggedInUser())
      .catch(Alert.warning("즐겨찾기 삭제가 실패했습니다."));
  };

  return (
    <Link to={`/cocktails/${cocktail.id}`} className="my-favorite">
      <div className="my-favorite-image">
        <img src={cocktail.imageUrl} alt={cocktail.name} />
      </div>
      <div className="my-favorite-name">{cocktail.name}</div>
      <div className="delete-favorite-container" onClick={onDeleteFavorite}>
        <img
          className="trash-bin-image"
          src="/image/trash-bin.png"
          alt="trash-bin"
        />
      </div>
    </Link>
  );
};

export default MyFavorite;
