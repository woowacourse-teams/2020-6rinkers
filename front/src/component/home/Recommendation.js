import React from "react";
import { Link } from "react-router-dom";

const Recommendation = () => {
  return (
    <Link to="/recommend">
      <div className="homeRecommendation">
        <div className="content">칵테일 추천 받기 🍸</div>
      </div>
    </Link>
  );
};

export default Recommendation;
