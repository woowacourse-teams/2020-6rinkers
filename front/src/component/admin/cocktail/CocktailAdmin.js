import React, { useState } from "react";
import { Redirect, useLocation } from "react-router-dom";
import CocktailEditFormContainer from "./CocktailEditFormContainer";
import { fetchCocktail } from "../../../api";
import { convertCocktailToInputData } from "../../../utils/admin/cocktailConverter";
import CocktailListContainer from "./CocktailListContainer";
import {
  DEFAULT_COCKTAIL_DATA,
  EMPTY_COCKTAIL_DATA,
} from "../../../utils/admin/constant";
import "../../../css/admin/admin.css";

const CocktailAdmin = ({ role }) => {
  const [cocktail, setCocktail] = useState(DEFAULT_COCKTAIL_DATA);

  const onUpdateCocktail = (value, name) => {
    setCocktail({
      ...cocktail,
      [name]: value,
    });
  };

  const updateFromSelectedCocktail = async (e) => {
    const selectedCocktail = await fetchCocktail(
      e.currentTarget.dataset.cocktailId
    );
    const inputCocktail = convertCocktailToInputData(selectedCocktail.data);
    setCocktail(inputCocktail);
  };

  const onResetCocktail = () => {
    setCocktail(EMPTY_COCKTAIL_DATA);
  };

  const location = useLocation();
  if (!role) {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  } else if (role !== "ROLE_ADMIN") {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  }

  return (
    <div className="admin">
      <div className="editFormContainer">
        <CocktailEditFormContainer
          cocktail={cocktail}
          onUpdateCocktail={onUpdateCocktail}
          onResetCocktail={onResetCocktail}
        />
      </div>
      <div className="cocktailListContainer">
        <CocktailListContainer
          cocktail={cocktail}
          updateFromSelectedCocktail={updateFromSelectedCocktail}
        />
      </div>
    </div>
  );
};

export default CocktailAdmin;
