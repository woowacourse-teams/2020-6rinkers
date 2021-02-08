import { useRecoilValue } from "recoil";
import { ingredientsAdminState } from "../../../recoil";
import IngredientItem from "./IngredientItem";
import React from "react";

const IngredientListContainer = () => {
  const ingredientsAdmin = useRecoilValue(ingredientsAdminState);

  return (
    <div className="ingredient-list-container">
      {ingredientsAdmin &&
        ingredientsAdmin.map((ingredient, index) => (
          <IngredientItem key={`ingredient-${index}`} ingredient={ingredient} />
        ))}
    </div>
  );
};

export default IngredientListContainer;
