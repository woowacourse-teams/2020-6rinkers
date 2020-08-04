import React, { useState } from "react";
import { Link } from "react-router-dom";
import { fetchTodayCocktail } from "../../api";

const TodayCocktail = () => {
  const [todayCocktail, setTodayCocktail] = useState({});

  const loadTodayCocktail = async () => {
    const response = await fetchTodayCocktail();
    const content = response["data"];
    setTodayCocktail(content);
  };

  useState(loadTodayCocktail, []);

  return (
    <div className="todayCocktailContainer">
      <div className="todayCocktailTitle">오늘의 칵테일</div>
      <Link to={`/cocktails/${todayCocktail.id}`}>
        <div className="todayCocktailImage">
          <img src={todayCocktail.imageUrl} alt={todayCocktail.name} />
        </div>
      </Link>
      <div className="todayCocktailName">{todayCocktail.name}</div>
    </div>
  );
};

export default TodayCocktail;
