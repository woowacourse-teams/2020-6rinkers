import React from "react";

const SearchedCocktail = ({ cocktail }) => {
  return (
    <div className="searchedCocktailContainer">
      <div className="searchedCocktailName">{cocktail.name}</div>
      <div className="searchedCocktailImage">
        <img src={cocktail.imageUrl} alt={cocktail.name} />
      </div>
      <div className="searchedCocktailTags">태그 태그 태그 태그 태그</div>
    </div>
  );
};

export default SearchedCocktail;
