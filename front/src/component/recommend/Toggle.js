import React from "react";

function Toggle({ name, answer, onToggleAnswer }) {
  return (
    <div
      onClick={() => onToggleAnswer(name)}
      className={answer[name] === "NO" ? "tag-name checked" : "tag-name"}
    >
      {name}
    </div>
  );
}

export default Toggle;
