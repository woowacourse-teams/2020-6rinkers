import React from "react";
import { useHistory } from "react-router-dom";
import Slider from "infinite-react-carousel";
import ResultSlide from "./ResultSlide";
import "../../css/recommend/result.css";

const Result = ({ cocktails }) => {
  const history = useHistory();

  if (cocktails.length === 0) {
    return (
      <div className="no-result-container">
        <h2>아쉽게도 결과가 없네요</h2>
        <h1>😥</h1>
        <button className="btn-result-main" onClick={() => history.push("/")}>
          메인으로 돌아가기
        </button>
      </div>
    );
  }

  return (
    <div className="result-container">
      <Slider>
        {cocktails.map((cocktail) => (
          <ResultSlide cocktail={cocktail} key={cocktail.id} />
        ))}
      </Slider>
    </div>
  );
};

export default Result;
