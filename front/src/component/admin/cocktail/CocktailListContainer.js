import React, { useEffect, useState } from "react";
import { fetchAllCocktails } from "../../../api";
import CocktailItem from "./CocktailItem";
import "../../../css/admin/cocktailListContainer.css";

const CocktailListContainer = ({
  cocktail,
  updateFromSelectedCocktail,
  onSetCocktailEdit,
}) => {
  const [cocktails, setCocktails] = useState([]);

  const onLoadCocktails = async () => {
    const response = await fetchAllCocktails();
    const content = response["data"];
    setCocktails(content);
  };

  useEffect(() => {
    onLoadCocktails();
  }, [cocktail]);

  return (
    <div className="cocktailsList">
      {cocktails.map((item, index) => (
        <CocktailItem
          cocktail={item}
          updateFromSelectedCocktail={updateFromSelectedCocktail}
          key={"cocktail" + index}
        />
      ))}
    </div>
  );
};

export default CocktailListContainer;
