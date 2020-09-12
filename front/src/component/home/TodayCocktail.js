import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchTodayCocktail } from "../../api";

const TodayCocktail = ({ cocktails, setCocktails, role }) => {
  const [todayCocktail, setTodayCocktail] = useState({});

  useEffect(() => {
    fetchTodayCocktail().then((response) => {
      setTodayCocktail(response.data);
    });
  }, []);

  return (
    <div className="todayCocktailContainer">
      <div className="todayCocktailTitle">오늘의 칵테일</div>
      <Link
        to={{
          pathname: `/cocktails/${todayCocktail.id}`,
          cocktails: cocktails,
          setCocktails: setCocktails,
          role: role,
        }}
      >
        <div className="todayCocktailImage">
          <img src={todayCocktail.imageUrl} alt={todayCocktail.name} />
        </div>
      </Link>
      <div className="todayCocktailName">{todayCocktail.name}</div>
    </div>
  );
};

export default TodayCocktail;
