import React, { useState } from "react";
import EditFormContainer from "./EditFormContainer";
import CocktailListContainer from "./CocktailListContainer";
import { DEFAULT_COCKTAIL_DATA } from "../../../utils/admin/constant";
import "../../../css/admin/cocktail/cocktailAdmin.css";

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
        <EditFormContainer
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
