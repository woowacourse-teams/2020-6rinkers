import React, { useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import { fetchAllCocktails } from "../../api";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([
    {
      // 테스트 용. 추후 제거 필요
      name: "블루하와이",
      imageUrl: "/image/blue-hawai.png",
    },
  ]);

  const onLoadCocktails = async () => {
    const response = await fetchAllCocktails();
    const content = response["data"];
    setCocktails(content);
  };

  useEffect(() => {
    onLoadCocktails();
  }, []);

  return (
    <div className="cocktailSearchContainer">
      <SearchContainer />
      <SearchedCocktails cocktails={cocktails} />
    </div>
  );
};

export default CocktailSearch;
