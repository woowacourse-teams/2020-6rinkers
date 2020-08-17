import React, { useState } from "react";
import { questions } from "./const";
import { useHistory } from "react-router-dom";
import { createRecommend } from "../../api";
import Dislike from "./Dislike";
import Intro from "./Intro";
import Concept from "./Concept";
import Abv from "./Abv";
import Ingredient from "./Ingredient";
import Taste from "./Taste";
import "../../css/recommend/question.css";

const Question = ({ setCocktails }) => {
  const [answers, setAnswers] = useState([]);
  const [stage, setStage] = useState(1);
  const [question, setQuestion] = useState(questions[stage - 1]);

  const addAnswer = (type, answer) => {
    const wrappedAnswer = { [type]: answer };
    if (stage !== 0) {
      setAnswers([...answers, wrappedAnswer]);
    }
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

  const renderAnswer = () => {
    switch (stage) {
      case 1:
        return <Intro addAnswer={addAnswer} />;
      case 2:
        return <Concept addAnswer={addAnswer} />;
      case 3:
        return <Abv addAnswer={addAnswer} />;
      case 4:
        return <Ingredient addAnswer={addAnswer} />;
      case 5:
        return <Taste addAnswer={addAnswer} />;
      case 6:
        return <Dislike addAnswer={addLastAnswer} />;
    }
  };

  return (
    <div className="question-container">
      <div className="question">
        {question.split("\n").map((line, index) => {
          return (
            <span key={`question-${index}`}>
              {line}
              <br />
            </span>
          );
        })}
      </div>
      {renderAnswer()}
    </div>
  );
};

export default Question;
