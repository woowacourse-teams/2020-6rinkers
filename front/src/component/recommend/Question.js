import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { questions } from "./const";
import { createRecommend } from "../../api";
import Dislike from "./Dislike";
import Intro from "./Intro";
import Concept from "./Concept";
import Abv from "./Abv";
import Ingredient from "./Ingredient";
import Taste from "./Taste";
import "../../css/recommend/question.css";

const INITIAL_STAGE = 1;

const Question = ({ setCocktails }) => {
  const [answers, setAnswers] = useState({});
  const [stage, setStage] = useState(INITIAL_STAGE);
  const [question, setQuestion] = useState(questions[stage - 1]);

  const addAnswer = (type, answer) => {
    const wrappedAnswer = { [type]: answer };
    if (stage !== INITIAL_STAGE) {
      setAnswers({ ...answers, ...wrappedAnswer });
    }
    setStage(stage + 1);
    setQuestion(questions[stage]);
  };

  const history = useHistory();

  const addLastAnswer = (type, answer) => {
    const wrappedAnswer = { [type]: answer };
    setAnswers(() => ({ ...answers, ...wrappedAnswer }));
  };

  const recommendCocktails = async () => {
    const response = await createRecommend(answers);
    setCocktails(response.data);
    history.push("/result");
  };

  useEffect(() => {
    const recommend = async () => {
      await recommendCocktails();
    };
    if (Object.keys(answers).length === 5) {
      recommend();
    }
  }, [answers]);

  const renderAnswer = () => {
    switch (stage) {
      case INITIAL_STAGE:
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
