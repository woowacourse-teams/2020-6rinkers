import React, {forwardRef} from "react";
import {Link} from "react-router-dom";

const AutoCocktailWord = forwardRef(({cocktail, highlight, index}, ref) => {
  return (
    <Link to={`/cocktails/${cocktail.id}`}>
      <li
        className={`autoCocktailWord ${highlight ? "highlight" : ""}`}
        data-id={cocktail.id}
        data-index={index}
        ref={ref}
      >
        {cocktail.name}
      </li>
    </Link>
  );
});

export default AutoCocktailWord;
