import React from "react";
import { Link } from "react-router-dom";
import "../../css/recommend/result.css";

const ResultSlide = ({ cocktail, key }) => {
  return (
    <div className="result-slide" key={key}>
      <div className="result-cocktail-name">{cocktail.name}</div>
      <Link to={`/cocktails/${cocktail.id}`}>
        <div className="result-cocktail-image">
          <img src={cocktail.imageUrl} alt={cocktail.name} />
        </div>
      </Link>
      <div className="result-cocktail-description">{cocktail.description}</div>
    </div>
  );
};

export default ResultSlide;
