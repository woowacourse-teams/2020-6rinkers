import React from "react";
import { answerList } from "./const";

const AnswerButton = ({ name, checked, onChangeAnswer }) => {
  return (
    <div>
      <button onClick={() => onChangeAnswer(name, "YES")} className={checked === "YES" ? "answer-button checked" : "answer-button"} >
        {answerList[0].yes}
      </button>
      <button onClick={() => onChangeAnswer(name ,"SOSO")} className={checked === "SOSO" ? "answer-button checked" : "answer-button"} >
        {answerList[0].soso}
      </button>
      <button onClick={() => onChangeAnswer(name, "NO")} className={checked === "NO" ? "answer-button checked" : "answer-button"} >
        {answerList[0].no}
      </button>
    </div>
  );
};

export default AnswerButton;
