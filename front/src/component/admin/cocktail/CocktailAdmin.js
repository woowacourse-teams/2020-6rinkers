import React, { useState } from "react";
import { Redirect, useLocation } from "react-router-dom";
import { useRecoilValue } from "recoil";
import CocktailEditFormContainer from "./CocktailEditFormContainer";
import { fetchCocktail } from "../../../api";
import { convertCocktailToInputData } from "../../../utils/admin/cocktailConverter";
import CocktailListContainer from "./CocktailListContainer";
import {
  DEFAULT_COCKTAIL_DATA,
  EMPTY_COCKTAIL_DATA,
} from "../../../utils/admin/constant";
import "../../../css/admin/admin.css";
import { userState } from "../../../recoil";

const CocktailAdmin = () => {
  const [cocktail, setCocktail] = useState(DEFAULT_COCKTAIL_DATA);
  const role = useRecoilValue(userState).currentUser.role;

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

  if (role !== "ROLE_ADMIN") {
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
      <div className="edit-form-container">
        <CocktailEditFormContainer
          cocktail={cocktail}
          onUpdateCocktail={onUpdateCocktail}
          onResetCocktail={onResetCocktail}
        />
      </div>
      <div className="cocktail-list-container">
        <CocktailListContainer
          cocktail={cocktail}
          updateFromSelectedCocktail={updateFromSelectedCocktail}
        />
      </div>
    </div>
  );
};

export default CocktailAdmin;
