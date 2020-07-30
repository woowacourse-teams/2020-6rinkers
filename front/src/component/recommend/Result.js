import React from "react";
import { useHistory } from "react-router-dom";
import Slider from "infinite-react-carousel";
import ResultSlide from "./ResultSlide";
import "../../css/recommend/result.css";

const Result = ({ cocktails }) => {
  const history = useHistory();

  if (!cocktails) {
    history.push("/question");
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
