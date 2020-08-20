import React from "react";

function Toggle({ name, tagId, answer, onToggleAnswer }) {
  return (
    <div
      onClick={() => onToggleAnswer(tagId)}
      className={answer[tagId] === "NO" ? "tag-name checked" : "tag-name"}
    >
      {name}
    </div>
  );
}

export default Toggle;
