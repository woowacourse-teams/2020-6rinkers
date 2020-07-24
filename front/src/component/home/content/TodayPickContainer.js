import React from "react";
import TodayPickCard from "./TodayPickCard";
import TodayPickImage from "./TodayPickImage";
import TodayPickCover from "./TodayPickCover";

const TodayPickContainer = () => {
  return (
    <div className="today-pick-container">
      <TodayPickCard />
      <TodayPickImage />
      <TodayPickCover />
    </div>
  );
};

export default TodayPickContainer;
