import React from "react";
import TodayCocktail from "./TodayCocktail";
import "../../css/home/home.css";
import Recommendation from "./Recommendation";

const Home = ({ role }) => {
  return (
    <div className="home">
      <TodayCocktail role={role} />
      <Recommendation />
    </div>
  );
};

export default Home;
