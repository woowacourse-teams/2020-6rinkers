import {
  ingredientAdminState,
  ingredientsAdminState,
  userState,
} from "../../../recoil";
import { useRecoilState, useRecoilValue } from "recoil";
import { Redirect, useLocation } from "react-router-dom";
import { fetchAllIngredients } from "../../../api";
import React, { useEffect } from "react";
import "../../../css/admin/ingredientAdmin.css";
import IngredientEditFormContainer from "./IngredientEditFormContainer";
import IngredientListContainer from "./IngredientListContainer";

const IngredientAdmin = (props) => {
  const role = useRecoilValue(userState).currentUser.role;
  const [ingredientAdmin, setIngredientAdmin] = useRecoilState(
    ingredientAdminState
  );

  const [ingredientsAdmin, setIngredientsAdmin] = useRecoilState(
    ingredientsAdminState
  );
  const location = useLocation();

  const fetchIngredients = async () => {
    const response = await fetchAllIngredients();
    setIngredientsAdmin(response.data);
  };

  useEffect(() => {
    fetchIngredients();
  }, [ingredientAdmin]);

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

  const onChange = (e) => {
    const { value, name } = e.target;
    setIngredientAdmin({ ...ingredientAdmin, [name]: value });
  };

  return (
    <div className="ingredient-admin">
      <IngredientEditFormContainer onChange={onChange} />
      <IngredientListContainer />
    </div>
  );
};

export default IngredientAdmin;
