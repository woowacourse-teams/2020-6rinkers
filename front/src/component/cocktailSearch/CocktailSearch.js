import React from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";

const CocktailSearch = () => {
  return (
    <div className="cocktailSearchContainer">
      <SearchContainer />
      <SearchedCocktails />
    </div>
  );
};

export default CocktailSearch;
