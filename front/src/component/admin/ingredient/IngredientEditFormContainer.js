import { ingredientAdminState } from "../../../recoil";
import { useRecoilValue } from "recoil";
import { createIngredient, updateIngredient } from "../../../api";
import React from "react";

const IngredientEditFormContainer = ({ onChange }) => {
  const ingredientAdmin = useRecoilValue(ingredientAdminState);

  const saveOrUpdateIngredientAdmin = async () => {
    if (ingredientAdmin.id === 0 || ingredientAdmin.id === "0") {
      await createIngredient(ingredientAdmin);
    } else {
      await updateIngredient(ingredientAdmin.id, ingredientAdmin);
    }
  };

  return (
    <div className="ingredients-edit-form-container">
      {Object.keys(ingredientAdmin).map((key, index) => (
        <div className="form-row" key={`ingredient-input-${index}`}>
          <div className="ingredient-key">{key}</div>
          <input
            className="ingredient-input"
            type="text"
            name={key}
            value={ingredientAdmin[key]}
            onChange={onChange}
          />
        </div>
      ))}
      <button className="submit-btn" onClick={saveOrUpdateIngredientAdmin}>
        저장/수정하기
      </button>
    </div>
  );
};

export default IngredientEditFormContainer;
