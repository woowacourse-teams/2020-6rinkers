import React, { useEffect, useState } from "react";
import { deleteCocktail, fetchAllCocktails } from "../../../api";
import CocktailItem from "./CocktailItem";
import "../../../css/admin/cocktailListContainer.css";

const CocktailListContainer = ({ cocktail, updateFromSelectedCocktail }) => {
  const [cocktails, setCocktails] = useState([]);

  const onLoadCocktails = async () => {
    const response = await fetchAllCocktails();
    const content = response.data;
    setCocktails(content);
  };

  const onDeleteCocktail = async (id, e) => {
    e.stopPropagation();
    const response = await deleteCocktail(id);
    alert(response.status === 204 ? "삭제 성공" : "삭제 실패");
  };

  useEffect(() => {
    onLoadCocktails();
  }, [cocktail]);

  return (
    <div className="cocktails-list">
      {cocktails.map((item, index) => (
        <CocktailItem
          cocktail={item}
          updateFromSelectedCocktail={updateFromSelectedCocktail}
          onDeleteCocktail={onDeleteCocktail}
          key={"cocktail" + index}
        />
      ))}
    </div>
  );
};

export default CocktailListContainer;
