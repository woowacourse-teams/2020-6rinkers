import React from "react";
import SearchedCocktail from "./SearchedCocktail";

const SearchedCocktails = ({ cocktails }) => {
  return (
    <div className="searched-cocktails-container">
      {cocktails.length !== 0 &&
        cocktails.map((cocktail, index) => (
          <SearchedCocktail key={index} cocktail={cocktail} />
        ))}
    </div>
  );
};

export default SearchedCocktails;
