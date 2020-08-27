import React from "react";
import { Link } from "react-router-dom";
import Alert from "react-s-alert";
import CircularBox from "../common/CircularBox";
import CocktailFavorite from "./CocktailFavorite";
import { addFavorite } from "../../api/index";

const SearchedCocktail = ({ cocktail, cocktails, setCocktails, role }) => {
  return (
    <div
      className="searchedCocktailContainer"
      data-search-cocktail={cocktail.id}
    >
      <div className="cocktailNameWithFavorite">
        <div className="emptyName" />
        <div className="searchedCocktailName">{cocktail.name}</div>
        <div className="favoriteContainer">
          {1 ? (
            <CocktailFavorite
              cocktail={cocktail}
              cocktails={cocktails}
              setCocktails={setCocktails}
            />
          ) : (
            <div />
          )}
        </div>
      </div>
      <Link
        to={{
          pathname: `/cocktails/${cocktail.id}`,
          role: role,
        }}
      >
        <div className="searchedCocktailImage">
          <img src={cocktail.imageUrl} alt={cocktail.name} />
        </div>
      </Link>
      <div className="searchedCocktailTags">
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
