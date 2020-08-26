import React from "react";
import MyFavorite from "./MyFavorite";

const MyFavoritesContainer = ({ myFavorites, loadCurrentlyLoggedInUser }) => {
  return (
    <div className="my-favorites-container">
      <div className="my-favorites-title">내가 좋아하는 칵테일</div>
      <div className="my-favorites">
        {myFavorites &&
          myFavorites.map((myFavorite, index) => {
            return (
              <>
                <MyFavorite
                  cocktail={myFavorite}
                  key={"favorite" + index}
                  loadCurrentlyLoggedInUser={loadCurrentlyLoggedInUser}
                />
                <div className="divider" />
              </>
            );
          })}
      </div>
    </div>
  );
};

export default MyFavoritesContainer;
