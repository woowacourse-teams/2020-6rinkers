import React from "react";
import TodayCocktail from "./TodayCocktail";
import "../../css/home/home.css";
import Recommendation from "./Recommendation";

const Home = ({ cocktails, setCocktails, role }) => {
  return (
    <div className="home">
      <TodayCocktail
        cocktails={cocktails}
        setCocktails={setCocktails}
        role={role}
      />
      <Recommendation />
    </div>
  );
};

export default Home;
