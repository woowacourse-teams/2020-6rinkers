import React from "react";
import TodayPickHeader from "./TodayPickHeader";
import TodayPickContent from "./TodayPickContent";

const TodayPickCard = () => {
  return (
    <div className="card">
      <TodayPickHeader />
      <TodayPickContent
        name="블루하와이"
        tags={["럼", "달달"]}
        detail="럼을 기반으로 파인애플과 코코넛의 새콤달콤한 맛과 푸른 빛이 감도는 칵테일입니다."
      />
    </div>
  );
};

export default TodayPickCard;
