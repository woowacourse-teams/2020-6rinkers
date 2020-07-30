import React from "react";
import { answerList } from "./const";

const AnswerButton = ({ addAnswer }) => {
  return (
    <div>
      <button onClick={() => addAnswer(true)} className="answer-button">
        {answerList[0].yes}
      </button>
      <button onClick={() => addAnswer(false)} className="answer-button">
        {answerList[0].no}
      </button>
      <button onClick={() => addAnswer(true)} className="answer-button">
        {answerList[0].soso}
      </button>
    </div>
  );
};

export default AnswerButton;
