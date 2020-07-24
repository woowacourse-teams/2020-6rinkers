import React from "react";

const TodayCocktail = (props) => {
  return (
    <div className="todayCocktailContainer">
      <div className="todayCocktailTitle">오늘의 칵테일</div>
      <div className="todayCocktailImage">
        <img src="/image/blue-hawai.png" alt="blueHawai" />
      </div>
      <div className="todayCocktailName">블루 하와이</div>
    </div>
  );
};

export default TodayCocktail;
