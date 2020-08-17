import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";

const Concept = ({ addAnswer }) => {
  const [concepts, setConcepts] = useState([]);
  const [answer, setAnswer] = useState({});

  useEffect(() => {
    // api
    const updateConcepts = async () => {
      await setConcepts(["분위기 있는", "기분전환이 필요할 때", "애인과 함께"]);
    };
    updateConcepts();
  }, []);

  useEffect(() => {
    if (concepts.length === 0) {
      return;
    }
    const updateAnswer = async () => {
      await setAnswer(
        concepts.reduce((o, key) => ({ ...o, [key]: "SOSO" }), {})
      );
    };
    updateAnswer();
  }, [concepts]);

  const onChangeAnswer = (name, userAnswer) => {
    setAnswer({
      ...answer,
      [name]: userAnswer,
    });
  };

  return (
    <div>
      {concepts &&
        concepts.map((concept, index) => {
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
        type="moodAnswers"
        answer={answer}
        addAnswer={addAnswer}
        saying="다음 질문으로"
      />
    </div>
  );
};

export default Concept;
