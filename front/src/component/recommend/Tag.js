import React from "react";
import AnswerButton from "./AnswerButton";

function Tag({ name, answer, onChangeAnswer }) {

  return (
    <div className="tag-container">
      <div className="tag-name">{name}</div>
      <AnswerButton name={name} checked={answer[name]} onChangeAnswer={onChangeAnswer}/>
    </div>
  );
}

export default Tag;
