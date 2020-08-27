import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";
import { fetchThreeRandomConceptTags } from "../../api";

const Concept = ({ addAnswer }) => {
  const [concepts, setConcepts] = useState([]);
  const [answer, setAnswer] = useState([]);

  const updateConcepts = async () => {
    const response = await fetchThreeRandomConceptTags();
    const data = response["data"];
    await setConcepts(data);
  };
  useEffect(() => {
    updateConcepts();
  }, []);

  const updateAnswer = async () => {
    if (concepts.length === 0) {
      return;
    }
    await setAnswer(
      answer.concat(
        concepts.map((concept) => ({
          tagId: concept.tagId,
          userPreferenceAnswer: "",
        }))
      )
    );
  };

  useEffect(() => {
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

  const checkDone = () => {
    return answer.filter((ans) => ans.userPreferenceAnswer === "").length === 0;
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
        done={checkDone()}
      />
    </div>
  );
};

export default Concept;
