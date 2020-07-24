import React from "react";
import blueHawaii from "./blue-hawaii.jpg";
import "../css/RandomCocktail.css";
import FindTasteButton from "./FindTasteButton";

const RandomCocktail = () => {
  return (
    <div className="random-cocktail">
      <div className="content">
        <p className="title">오늘의 칵테일</p>
        <img src={blueHawaii} alt="blue-hawaii" className="cocktail-img" />
        <p className="cocktail-name">블루 하와이</p>
      </div>
      <FindTasteButton />
    </div>
  );
};

export default RandomCocktail;
