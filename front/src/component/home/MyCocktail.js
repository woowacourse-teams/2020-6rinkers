import React, { useEffect } from "react";
import { myCocktailButtonClickInduceAlert } from "../alert/Alerts";
import { Link } from "react-router-dom";

const MyCocktail = () => {
  useEffect(() => {
    myCocktailButtonClickInduceAlert();
  }, []);

  return (
    <Link className="create-button" to="/my-cocktail">
      <div className="content">나만의 칵테일 만들기 🍹</div>
    </Link>
  );
};

export default MyCocktail;
