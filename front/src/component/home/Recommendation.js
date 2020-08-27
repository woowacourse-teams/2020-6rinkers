import React from "react";
import { Link } from "react-router-dom";

const Recommendation = (props) => {
  return (
    <div className="homeRecommendation">
      <Link to="/recommend">
        <div className="content">칵테일 추천 받기 🍸</div>
      </Link>
    </div>
  );
};

export default Recommendation;
