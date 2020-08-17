import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";

const Taste = ({ addAnswer }) => {
  const tastes = ["sweetAnswer", "sourAnswer", "bitterAnswer"];
  const [answer, setAnswer] = useState({
    sweetAnswer: "",
    sourAnswer: "",
    bitterAnswer: "",
  });

  const onChangeAnswer = (name, userAnswer) => {
    setAnswer({
      ...answer,
      [name]: userAnswer,
    });
  };

  return (
    <div>
      {tastes.map((concept, index) => {
        return (
          <Tag
            name={concept}
            key={`key-${index}`}
            answer={answer}
            onChangeAnswer={onChangeAnswer}
          />
        );
      })}
      <NextStage
        type="flavorAnswer"
        answer={answer}
        addAnswer={addAnswer}
        saying="마지막 질문으로"
      />
    </div>
  );
};

export default Taste;
