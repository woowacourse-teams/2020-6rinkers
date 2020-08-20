import React from "react";
import AnswerButton from "./AnswerButton";

function Tag({ name, tagId, answer, onChangeAnswer }) {

  return (
    <div className="tag-container">
      <div className="tag-name">{name}</div>
      <AnswerButton name={name} tagId={tagId} checked={answer[tagId]} onChangeAnswer={onChangeAnswer}/>
    </div>
  );
}

export default Tag;
