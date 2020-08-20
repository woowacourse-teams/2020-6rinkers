import React from "react";
import { answerList } from "./const";

const AnswerButton = ({ name, tagId, checked, onChangeAnswer }) => {
  return (
    <div>
      <button onClick={() => onChangeAnswer(tagId, "YES")} className={checked === "YES" ? "answer-button checked" : "answer-button"} >
        {answerList[0].yes}
      </button>
      <button onClick={() => onChangeAnswer(tagId ,"SOSO")} className={checked === "SOSO" ? "answer-button checked" : "answer-button"} >
        {answerList[0].soso}
      </button>
      <button onClick={() => onChangeAnswer(tagId, "NO")} className={checked === "NO" ? "answer-button checked" : "answer-button"} >
        {answerList[0].no}
      </button>
    </div>
  );
};

export default AnswerButton;
