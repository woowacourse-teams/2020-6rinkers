import React, { useState } from "react";
import StepProgressBar from "./StepProgressBar";
import "react-step-progress-bar/styles.css";
import "../../css/userCocktail/userCocktail.css";
import Question from "./Question";
import Name from "./Name";
import Ingredient from "./Ingredient";
import Quantity from "./Quantity";
import Recipe from "./Recipe";
import QuantityUnit from "./QuantityUnit";
import UserCocktailItems from "./UserCocktailItems";

const UserCocktail = () => {
  const INITIAL_STAGE = "";
  const [stage, setStage] = useState(INITIAL_STAGE);

  const renderContents = () => {
    switch (stage) {
      case INITIAL_STAGE:
        return <UserCocktailItems setStage={setStage} />;
      case "name":
        return <Name setStage={setStage} />;
      case "ingredients":
        return <Ingredient setStage={setStage} />;
      case "quantity-unit":
        return <QuantityUnit setStage={setStage} />;
      case "quantity":
        return <Quantity setStage={setStage} />;
      case "recipe":
        return <Recipe setStage={setStage} />;
    }
  };

  const convertToPercent = (stage) => {
    switch (stage) {
      case INITIAL_STAGE:
        return 0;
      case "name":
        return 0;
      case "ingredients":
        return 25;
      case "quantity-unit":
        return 50;
      case "quantity":
        return 75;
      case "recipe":
        return 100;
    }
  };

  return (
    <div className="user-cocktail">
      {stage !== INITIAL_STAGE && (
        <StepProgressBar percent={convertToPercent(stage)} />
      )}
      <Question stage={stage} />
      {renderContents()}
    </div>
  );
};

export default UserCocktail;
