import React from "react";
import StepProgressBar from "./StepProgressBar";
import "react-step-progress-bar/styles.css";
import "../../css/userCocktail/userCocktail.css";
import Question from "./Question";

const UserCocktail = () => {
  return (
    <div className="user-cocktail">
      <StepProgressBar percent={"34"} />
      <Question stage="name" />
    </div>
  );
};

export default UserCocktail;
