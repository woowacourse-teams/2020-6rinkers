import React from "react";
import { userCocktailQuestions } from "../const";

const Question = ({ stage }) => {
  return (
    <div className="question-container">
      <a className="question">{userCocktailQuestions[stage]}</a>
    </div>
  );
};

export default Question;
