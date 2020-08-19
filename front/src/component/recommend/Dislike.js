import React, { useEffect, useState } from "react";
import NextStage from "./NextStage";
import Toggle from "./Toggle";

const Dislike = ({ addAnswer }) => {
  const [dislikes, setDislikes] = useState([]);
  const [answer, setAnswer] = useState({});

  useEffect(() => {
    const updateDislikes = async () => {
      // api
      await setDislikes([
        "달걀",
        "민트",
        "생강",
        "소금 리밍",
        "아몬드",
        "압상트",
        "에너지드링크",
        "올리브",
        "와인",
        "우유",
        "초코",
        "카페인",
        "커피",
        "콜라",
        "크림",
        "토마토",
        "후추",
      ]);
    };
    updateDislikes();
  }, []);

  useEffect(() => {
    if (dislikes.length === 0) {
      return;
    }
    const updateAnswer = async () => {
      await setAnswer(
        dislikes.reduce((o, key) => ({ ...o, [key]: "YES" }), {})
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
                name={dislike}
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
