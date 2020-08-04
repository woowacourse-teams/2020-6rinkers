import React from "react";
import { Link } from "react-router-dom";
import CircularBox from "../common/CircularBox";

const SearchedCocktail = ({ cocktail }) => {
  return (
    <div
      className="searchedCocktailContainer"
      data-search-cocktail={cocktail.id}
    >
      <div className="searchedCocktailName">{cocktail.name}</div>
      <Link to={`/cocktails/${cocktail.id}`}>
        <div className="searchedCocktailImage">
          <img src={cocktail.imageUrl} alt={cocktail.name} />
        </div>
      </Link>
      <div className="searchedCocktailTags">
        {cocktail.tags &&
          cocktail.tags
            .slice(0, 4)
            .map((tag, index) => (
              <CircularBox key={"tag" + index} text={tag.name} />
            ))}
      </div>
    </div>
  );
};

export default SearchedCocktail;
