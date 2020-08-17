import React from "react";
import NextStage from "./NextStage";

const Intro = ({ addAnswer }) => {
  return (
    <div>
      <NextStage addAnswer={addAnswer} saying="첫 질문 받기" />
    </div>
  );
};

export default Intro;
