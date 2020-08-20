import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";

const Taste = ({ addAnswer }) => {
  const tastes = [
    { tagId: "sweetAnswer", name: "단맛" },
    { tagId: "sourAnswer", name: "신맛" },
    { tagId: "bitterAnswer", name: "쓴맛" },
  ];
  const [answer, setAnswer] = useState({
    sweetAnswer: "SOSO",
    sourAnswer: "SOSO",
    bitterAnswer: "SOSO",
  });

  const onChangeAnswer = (name, userAnswer) => {
    setAnswer({
      ...answer,
      [name]: userAnswer,
    });
  };

  return (
    <div>
      {tastes.map((taste, index) => {
        return (
          <Tag
            name={taste.name}
            tagId={taste.tagId}
            key={`key-${index}`}
            answer={answer[taste.tagId]}
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
