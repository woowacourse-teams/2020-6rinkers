import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchTodayCocktail } from "../../api";

const TodayCocktail = () => {
  const [todayCocktail, setTodayCocktail] = useState({});

  useEffect(() => {
    fetchTodayCocktail().then((response) => {
      setTodayCocktail(response.data);
    });
  }, []);

  return (
    <div className="today-cocktail-container">
      <div className="today-cocktail-title">오늘의 칵테일</div>
      <Link to={`/cocktails/${todayCocktail.id}`}>
        <div className="today-cocktail-image">
          <img src={todayCocktail.imageUrl} alt={todayCocktail.name} />
        </div>
      </Link>
      <div className="today-cocktail-name">{todayCocktail.name}</div>
    </div>
  );
};

export default TodayCocktail;
