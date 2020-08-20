import React, { useEffect, useState } from "react";
import NextStage from "./NextStage";
import Toggle from "./Toggle";
import { fetchDislikeTags } from "../../api";

const Dislike = ({ addAnswer }) => {
  const [dislikes, setDislikes] = useState([]);
  const [answer, setAnswer] = useState([]);

  useEffect(() => {
    const updateDislikes = async () => {
      const response = await fetchDislikeTags();
      const data = response["data"];
      await setDislikes(data);
    };
    updateDislikes();
  }, []);

  useEffect(() => {
    if (dislikes.length === 0) {
      return;
    }
    const updateAnswer = async () => {
      await setAnswer(
        answer.concat(
          dislikes.map((dislike) => ({
            tagId: dislike.tagId,
            userPreferenceAnswer: "YES",
          }))
        )
      );
    };
    updateAnswer();
  }, [dislikes]);

  const onToggleAnswer = (tagId) => {
    const clonedAnswer = [...answer];
    const targetAnswer = clonedAnswer.find((each) => each.tagId === tagId);
    targetAnswer.userPreferenceAnswer =
      targetAnswer.userPreferenceAnswer === "YES" ? "NO" : "YES";
    setAnswer(clonedAnswer);
  };

  const checkClicked = (tagId) => {
    const targetAnswer = answer.find((each) => each.tagId === tagId);
    return targetAnswer ? targetAnswer.userPreferenceAnswer : "";
  };

  return (
    <div className="dislike-container">
      <div className="toggles">
        {dislikes &&
          dislikes.map((dislike, index) => {
            return (
              <Toggle
                name={dislike.name}
                tagId={dislike.tagId}
                key={`key-${index}`}
                answer={checkClicked(dislike.tagId)}
                onToggleAnswer={onToggleAnswer}
              />
            );
          })}
      </div>
      <NextStage
        type="nonPreferenceAnswers"
        answer={answer}
        addAnswer={addAnswer}
        saying="칵테일 추천 받기"
      />
    </div>
  );
};

export default Dislike;
