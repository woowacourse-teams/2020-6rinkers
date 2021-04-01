import React from "react";
import TodayCocktail from "./TodayCocktail";
import "../../css/home/home.css";
import MyCocktail from "./MyCocktail";

const Home = () => {
  return (
    <div className="home">
      <TodayCocktail />
      <MyCocktail />
    </div>
  );
};

export default Home;
