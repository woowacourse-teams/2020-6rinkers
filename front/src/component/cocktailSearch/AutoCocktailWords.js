import React from "react";
import AutoCocktailWord from "./AutoCocktailWord";

const AutoCocktailWords = ({ cocktails, highLight, updateHighLight }) => {
  const onMouseOver = (e) => {
    const index = parseInt(e.target.dataset.index);
    if (isNaN(index)) {
      return;
    }
    updateHighLight(index);
  };

  return (
    <ul className="autoCocktailWords" onMouseOver={onMouseOver}>
      {cocktails.map((cocktail, index) => (
        <AutoCocktailWord
          key={cocktail.id}
          cocktail={cocktail}
          highLight={highLight === index}
          index={index}
        />
      ))}
    </ul>
  );
};

export default AutoCocktailWords;
