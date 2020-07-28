import React, {useEffect, useState} from "react";
import {fetchAllCocktails} from "../../../api";
import CocktailItem from "./CocktailItem";
import "../../../css/admin/cocktail/cocktailListContainer.css";

const CocktailListContainer = ({cocktail}) => {
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
      <div className="cocktailsListContainer">
        {cocktails.map((item, index) => (
            <CocktailItem cocktail={item} key={"cocktail" + index}/>
        ))}
      </div>
  );
};

export default CocktailListContainer;
