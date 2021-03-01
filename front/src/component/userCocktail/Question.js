import React from "react";
import { userCocktailQuestions } from "../const";

const Question = ({ stage }) => {
  return <div>{userCocktailQuestions[stage]}</div>;
};

export default Question;
