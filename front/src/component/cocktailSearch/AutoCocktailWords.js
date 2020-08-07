import React from "react";
import AutoCocktailWord from "./AutoCocktailWord";

const AutoCocktailWords = ({cocktails, highlight, updateHighlight}) => {
  const onMouseOver = (e) => {
    const index = parseInt(e.target.dataset.index);
    if (isNaN(index)) {
      return;
    }
    updateHighlight(index);
  };

  return (
    <ul className="autoCocktailWords" onMouseOver={onMouseOver}>
      {cocktails.map((cocktail, index) => (
        <AutoCocktailWord
          key={cocktail.id}
          cocktail={cocktail}
          highlight={highlight === index}
          index={index}
        />
      ))}
    </ul>
  );
};

export default AutoCocktailWords;
