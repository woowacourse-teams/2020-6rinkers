import React from "react";
import { Link } from "react-router-dom";

const Recommendation = (props) => {
  return (
    <div className="homeRecommendation">
      <Link to="/recommend">
        <div className="content">나를 위한 칵테일을 추천 받아 보아요 🍸</div>
      </Link>
    </div>
  );
};

export default Recommendation;
