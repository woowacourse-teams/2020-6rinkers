import React from "react";
import { Link } from "react-router-dom";
import { userState } from "../../recoil";
import { useRecoilValue } from "recoil/dist";
import CircularBox from "../common/CircularBox";
import CocktailFavorite from "./CocktailFavorite";

const SearchedCocktail = ({ cocktail }) => {
  const currentUser = useRecoilValue(userState).currentUser;

  return (
    <div
      className="searched-cocktail-container"
      data-search-cocktail={cocktail.id}
    >
      <Link to={`/cocktails/${cocktail.id}`}>
        <div className="searched-cocktail-name">{cocktail.name}</div>
      </Link>

      <div className="favorite-container">
        {currentUser.role ? (
          <CocktailFavorite cocktailId={cocktail.id} />
        ) : (
          <div />
        )}
      </div>
      <Link to={`/cocktails/${cocktail.id}`}>
        <div className="searched-cocktail-image">
          <img src={cocktail.imageUrl} alt={cocktail.name} />
        </div>
      </Link>
      <div className="searched-cocktail-tags">
        {cocktail.tags &&
          cocktail.tags
            .sort((a, b) => a.name.length - b.name.length)
            .slice(0, 4)
            .map((tag, index) => (
              <CircularBox key={"tag" + index} text={tag.name} />
            ))}
      </div>
    </div>
  );
};

export default SearchedCocktail;
