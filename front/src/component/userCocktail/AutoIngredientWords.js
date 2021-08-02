import React, { useRef } from "react";
import AutoIngredientWord from "./AutoIngredientWord";
import ScrollFocus from "../cocktailSearch/ScrollFocus";

const AutoIngredientWords = ({
  ingredients,
  highlightIndex,
  updateHighlight,
  onMouseDown,
}) => {
  const refOfIngredientHighlight = useRef();

  const onMouseOver = (e) => {
    const index = parseInt(e.target.dataset.index);
    if (isNaN(index)) {
      return;
    }
    updateHighlight(index);
  };

  const onMouseLeave = () => {
    updateHighlight(-1);
  };

  return (
    <ul
      className="auto-ingredient-words"
      onMouseOver={onMouseOver}
      onMouseLeave={onMouseLeave}
      onMouseDown={onMouseDown}
    >
      <ScrollFocus refOfHighlight={refOfIngredientHighlight} />
      {ingredients.map((ingredient, index) => (
        <AutoIngredientWord
          key={ingredient.id}
          ingredient={ingredient}
          highlight={highlightIndex === index}
          index={index}
          ref={highlightIndex === index ? refOfIngredientHighlight : null}
        />
      ))}
    </ul>
  );
};

export default AutoIngredientWords;
