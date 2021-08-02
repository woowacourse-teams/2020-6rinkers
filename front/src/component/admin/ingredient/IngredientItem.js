import { useSetRecoilState } from "recoil";
import { ingredientAdminState } from "../../../recoil";
import { deleteIngredient, fetchIngredient } from "../../../api";
import { INGREDIENT_ADMIN_PROTOTYPE } from "../../../constants";
import React from "react";

const IngredientItem = ({ ingredient }) => {
  const setIngredientAdmin = useSetRecoilState(ingredientAdminState);

  const onUpdateIngredientAdmin = async (e) => {
    e.preventDefault();
    const response = await fetchIngredient(ingredient.id);
    setIngredientAdmin(response.data);
  };

  const onDeleteIngredientAdmin = async (e) => {
    e.stopPropagation();
    await deleteIngredient(ingredient.id);
    setIngredientAdmin(INGREDIENT_ADMIN_PROTOTYPE);
  };

  return (
    <div className="ingredient-item" onClick={onUpdateIngredientAdmin}>
      <div className="ingredient-information">
        <div>name: {ingredient.name}</div>
        <div>color: {ingredient.color}</div>
        <div>abv: {ingredient.abv}</div>
      </div>
      <button onClick={onDeleteIngredientAdmin}>삭제</button>
    </div>
  );
};

export default IngredientItem;
