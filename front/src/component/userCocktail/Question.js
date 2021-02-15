import React from "react";
import { userCocktailQuestions } from "../recommend/const";

const Question = ({ stage }) => {
  return <div>{userCocktailQuestions[stage]}</div>;
};

export default Question;
