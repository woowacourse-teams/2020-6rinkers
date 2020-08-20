import React, { useEffect, useState } from "react";
import NextStage from "./NextStage";
import Toggle from "./Toggle";
import { fetchDislikeTags } from "../../api";

const Dislike = ({ addAnswer }) => {
  const [dislikes, setDislikes] = useState([]);
  const [answer, setAnswer] = useState({});

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
        dislikes.reduce((o, key) => ({ ...o, [key.tagId]: "YES" }), {})
      );
    };
    updateAnswer();
  }, [dislikes]);

  const onToggleAnswer = (name) => {
    setAnswer({
      ...answer,
      [name]: answer[name] === "YES" ? "NO" : "YES",
    });
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
                answer={answer}
                onToggleAnswer={onToggleAnswer}
              />
            );
          })}
      </div>
      <NextStage
        type="nonPreferenceAnswer"
        answer={answer}
        addAnswer={addAnswer}
        saying="칵테일 추천 받기"
      />
    </div>
  );
};

export default Dislike;
