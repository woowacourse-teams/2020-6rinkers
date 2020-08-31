import React from "react";
import MyFavorite from "./MyFavorite";

const MyFavoritesContainer = ({ myFavorites }) => {
  const slicedFavorites = myFavorites ? myFavorites.slice() : [];
  slicedFavorites.sort((a, b) => {
    return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
  });
  return (
    <div className="my-favorites-container">
      <div className="my-favorites-title">
        <h3>내가 좋아하는 칵테일</h3>
      </div>
      <div className="my-favorites">
        {slicedFavorites &&
          slicedFavorites.map((myFavorite, index) => {
            return (
              <>
                <MyFavorite cocktail={myFavorite} key={"favorite" + index} />
                <div className="divider" />
              </>
            );
          })}
      </div>
    </div>
  );
};

export default MyFavoritesContainer;
