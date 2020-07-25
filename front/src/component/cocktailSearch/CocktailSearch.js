import React from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";

const CocktailSearch = () => {
  return (
    <div className="cocktailSearchContainer">
      <div className="search">검색하기 창</div>
      <div className="searchTags">태그 보여주기 창</div>
      <SearchedCocktails />
    </div>
  );
};

export default CocktailSearch;
