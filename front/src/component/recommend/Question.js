import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import AnswerButton from "./AnswerButton";
import { questionList } from "./const";
import { createRecommend } from "../../api";
import "../../css/recommend/question.css";

const Question = ({ setCocktails }) => {
  const [answers, setAnswers] = useState([]);
  const [number, setNumber] = useState(1);
  const [question, setQuestion] = useState(questionList[number - 1]);

  const getCocktails = async () => {
    const response = await createRecommend(answers);
    setCocktails(response.data);
  };

  const addAnswer = (answer) => {
    setAnswers([...answers, { answer }]);
    setNumber(number + 1);
    setQuestion(questionList[number]);
  };

  const history = useHistory();

  const addLastAnswer = async (answer) => {
    setAnswers([...answers, answer]);
    await getCocktails(answers);
    history.push("/result");
  };

  return (
    <div className="question-container">
      <div className="question-number">Q{number}. / 10</div>
      <div className="question">{question}</div>
      {number !== 10 ? (
        <div className="question-answer-button-wrapper">
          <AnswerButton addAnswer={addAnswer} />
        </div>
      ) : (
        <div className="question-answer-button-wrapper">
          <AnswerButton addAnswer={addLastAnswer} />
        </div>
      )}
    </div>
  );
};

export default Question;
