import React from "react";
import SearchedCocktail from "./SearchedCocktail";

const SearchedCocktails = ({ cocktails, setCocktails, role }) => {
  return (
    <div className="searchedCocktailsContainer">
      {cocktails.length !== 0 &&
        cocktails.map((cocktail, index) => (
          <SearchedCocktail
            key={index}
            cocktail={cocktail}
            cocktails={cocktails}
            setCocktails={setCocktails}
            role={role}
          />
        ))}
    </div>
  );
};

export default SearchedCocktails;
