import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";
import { fetchThreeRandomConceptTags } from "../../api";

const Concept = ({ addAnswer }) => {
  const [concepts, setConcepts] = useState([]);
  const [answer, setAnswer] = useState([]);

  useEffect(() => {
    const updateConcepts = async () => {
      const response = await fetchThreeRandomConceptTags();
      const data = response["data"];
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
        answer.concat(
          concepts.map((concept) => ({
            tagId: concept.tagId,
            userPreferenceAnswer: "SOSO",
          }))
        )
      );
    };
    updateAnswer();
  }, [concepts]);

  const onChangeAnswer = (tagId, userAnswer) => {
    const clonedAnswer = [...answer];
    const targetAnswer = clonedAnswer.find((each) => each.tagId === tagId);
    targetAnswer.userPreferenceAnswer = userAnswer;
    setAnswer(clonedAnswer);
  };

  const checkClicked = (tagId) => {
    const targetAnswer = answer.find((each) => each.tagId === tagId);
    return targetAnswer ? targetAnswer.userPreferenceAnswer : "";
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
              answer={checkClicked(concept.tagId)}
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
