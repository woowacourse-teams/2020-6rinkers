import React from "react";
import { answerList } from "./const";

const AnswerButton = ({ tagId, answer, onChangeAnswer }) => {
  return (
    <div>
      <button
        onClick={() => onChangeAnswer(tagId, "YES")}
        className={answer === "YES" ? "answer-button checked" : "answer-button"}
      >
        {answerList[0].yes}
      </button>
      <button
        onClick={() => onChangeAnswer(tagId, "SOSO")}
        className={
          answer === "SOSO" ? "answer-button checked" : "answer-button"
        }
      >
        {answerList[0].soso}
      </button>
      <button
        onClick={() => onChangeAnswer(tagId, "NO")}
        className={answer === "NO" ? "answer-button checked" : "answer-button"}
      >
        {answerList[0].no}
      </button>
    </div>
  );
};

export default AnswerButton;
