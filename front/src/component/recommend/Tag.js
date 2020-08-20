import React from "react";
import AnswerButton from "./AnswerButton";

function Tag({ name, tagId, answer, onChangeAnswer }) {
  return (
    <div className="tag-container">
      <div className="tag-name">{name}</div>
      <AnswerButton
        tagId={tagId}
        answer={answer}
        onChangeAnswer={onChangeAnswer}
      />
    </div>
  );
}

export default Tag;
