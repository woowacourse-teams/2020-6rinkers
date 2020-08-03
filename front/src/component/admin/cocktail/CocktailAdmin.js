import React, { useState } from "react";
import CocktailEditFormContainer from "./CocktailEditFormContainer";
import CocktailListContainer from "./CocktailListContainer";
import { DEFAULT_COCKTAIL_DATA } from "../../../utils/admin/constant";
import "../../../css/admin/admin.css";

const CocktailAdmin = () => {
  const [cocktail, setCocktail] = useState(DEFAULT_COCKTAIL_DATA);

  const onUpdateCocktail = (value, name) => {
    setCocktail({
      ...cocktail,
      [name]: value,
    });
  };

  const onResetCocktail = () => {
    setCocktail(DEFAULT_COCKTAIL_DATA);
  };

  return (
    <div className="admin">
      <div className="editFormContainer">
        <CocktailEditFormContainer
          cocktail={cocktail}
          updateCocktail={onUpdateCocktail}
          onResetCocktail={onResetCocktail}
        />
      </div>
      <div className="cocktailListContainer">
        <CocktailListContainer cocktail={cocktail} />
      </div>
    </div>
  );
};

export default CocktailAdmin;
