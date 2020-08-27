import React from "react";

function NextStage({ type, answer, addAnswer, saying, done }) {
  if (done) {
    return (
      <button className="next-stage" onClick={() => addAnswer(type, answer)}>
        {saying}
      </button>
    );
  }
  return (
    <button
      className="next-stage"
      onClick={() => addAnswer(type, answer)}
      disabled
    >
      {saying}
    </button>
  );
}

export default NextStage;
