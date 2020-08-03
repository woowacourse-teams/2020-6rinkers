import React from "react";
import { Link } from "react-router-dom";

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
      <div className="searchedCocktailTags">태그 태그 태그 태그 태그</div>
    </div>
  );
};

export default SearchedCocktail;
