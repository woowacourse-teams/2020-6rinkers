import React, { useState } from "react";
import { questions } from "./stageInformation";
import { useHistory } from "react-router-dom";
import { createRecommend } from "../../api";
import Dislike from "./Dislike";
import Intro from "./Intro";
import Concept from "./Concept";
import Abv from "./Abv";
import Ingredient from "./Ingredient";
import Taste from "./Taste";
import NextStage from "./NextStage";
import "../../css/recommend/question.css";


const Question = ({ setCocktails }) => {
  const [answers, setAnswers] = useState([]);
  const [stage, setStage] = useState(1);
  const [question, setQuestion] = useState(questions[stage - 1]);

  const addAnswer = (answer) => {
    setAnswers([...answers, { answer }]);
    setStage(stage + 1);
    setQuestion(questions[stage]);
  };

  const history = useHistory();

  const getCocktails = async () => {
    const response = await createRecommend(answers);
    setCocktails(response.data);
  };

  const addLastAnswer = async (answer) => {
    setAnswers([...answers, answer]);
    await getCocktails(answers);
    history.push("/result");
  };

  const renderQuestion = () => {
    switch (stage) {
      case 1:
        return <Intro />;
      case 2:
        return <Concept question={question} />;
      case 3:
        return <Abv question={question} />;
      case 4:
        return <Ingredient question={question} />;
      case 5:
        return <Taste question={question} />;
      case 6:
        return <Dislike question={question} />;
    }
  };

  return (
    <div className="question-container">
      {renderQuestion()}
      {stage !== 6 ? (
        <NextStage addAnswer={addAnswer} />
      ) : (
        <NextStage addAnswer={addLastAnswer} />
      )}
    </div>
  );
};

export default Question;
