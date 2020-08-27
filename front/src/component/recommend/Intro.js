import React from "react";
import NextStage from "./NextStage";

const Intro = ({ addAnswer }) => {
  return (
    <div className="intro-container">
      <img className="bartender" src="/image/bartender.png" alt="bartender" />
      <NextStage addAnswer={addAnswer} saying="첫 질문 받기" done />
    </div>
  );
};

export default Intro;
