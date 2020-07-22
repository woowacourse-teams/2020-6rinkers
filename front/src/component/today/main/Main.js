import React from "react";
import TodayCocktail from "./TodayCocktail";
import TodayCocktailCaption from "./TodayCocktailCaption";
import blueHawaiiImage from "./blue-hawaii.png";
import "../../../css/todayMain.css";

const Main = () => {
  return (
    <div className="today-main">
      <TodayCocktail image={blueHawaiiImage} />
      <TodayCocktailCaption />
    </div>
  );
};

export default Main;
