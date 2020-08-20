import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";
import { fetchThreeRandomConceptTags } from "../../api";

const Concept = ({ addAnswer }) => {
  const [concepts, setConcepts] = useState([]);
  const [answer, setAnswer] = useState({});

  useEffect(() => {
    const updateConcepts = async () => {
      const response = await fetchThreeRandomConceptTags();
      const data = response["data"];
      console.log(data);
      await setConcepts(data);
    };
    updateConcepts();
  }, []);

  useEffect(() => {
    if (concepts.length === 0) {
      return;
    }
    const updateAnswer = async () => {
      await setAnswer(
        concepts.reduce((o, key) => ({ ...o, [key.tagId]: "SOSO" }), {})
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
              name={concept.name}
              tagId={concept.tagId}
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
