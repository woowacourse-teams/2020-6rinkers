import React from "react";

function NextStage({ type, answer, addAnswer, saying }) {
  return (
    <button className="next-stage" onClick={() => addAnswer(type, answer)}>
      {saying}
    </button>
  );
}

export default NextStage;
