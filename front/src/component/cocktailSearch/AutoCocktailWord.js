import React from "react";
import {Link} from "react-router-dom";

const AutoCocktailWord = ({cocktail, highlight, index}) => {
  return (
    <Link to={`/cocktails/${cocktail.id}`}>
      <li
        className={`autoCocktailWord ${highlight ? "highlight" : ""}`}
        data-id={cocktail.id}
        data-index={index}
      >
        {cocktail.name}
      </li>
    </Link>
  )
};

export default AutoCocktailWord;
