import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { recommendButtonClickInduceAlert } from "../alert/Alerts";

const Recommendation = () => {
  useEffect(() => {
    recommendButtonClickInduceAlert();
  }, []);

  return (
    <Link to="/recommend">
      <div className="home-recommendation">
        <div className="content">칵테일 추천 받기 🍸</div>
      </div>
    </Link>
  );
};

export default Recommendation;
