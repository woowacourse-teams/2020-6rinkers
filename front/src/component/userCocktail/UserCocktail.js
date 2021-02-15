import React from "react";
import StepProgressBar from "./StepProgressBar";
import "react-step-progress-bar/styles.css";
import "../../css/userCocktail/userCocktail.css";
import Question from "./Question";
import Name from "./Name";

const UserCocktail = () => {
  const currentPath = window.location.pathname.replace("/my-cocktail/", "");
  const INITIAL_PATH = "";

  const renderContents = () => {
    switch (currentPath) {
      case INITIAL_PATH:
        return <div> 처음 화면이니까 </div>;
      case "name":
        return <Name />;
      case "ingredients":
        return <div> ingredients 화면이니까 </div>;
      case "glass":
        return <div> glass 화면이니까 </div>;
      case "amount":
        return <div> amount 화면이니까 </div>;
      case "recipe":
        return <div> recipe 화면이니까 </div>;
    }
  };

  return (
    <div className="user-cocktail">
      {currentPath !== INITIAL_PATH && <StepProgressBar percent={"34"} />}
      <Question stage="name" />
      {renderContents()}
    </div>
  );
};

export default UserCocktail;
