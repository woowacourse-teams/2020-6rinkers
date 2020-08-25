import React from "react";

function Toggle({ name, tagId, answer, onToggleAnswer }) {
  return (
    <div
      onClick={() => onToggleAnswer(tagId)}
      className={answer === "NO" ? "tag-name checked line-through" : "tag-name"}
    >
      {name}
    </div>
  );
}

export default Toggle;
