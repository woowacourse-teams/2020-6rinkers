import React, { useRef } from "react";
import AutoCocktailWord from "./AutoCocktailWord";
import ScrollFocus from "./ScrollFocus";

const AutoCocktailWords = ({
  cocktails,
  highlightIndex,
  updateHighlight,
  onMouseDown,
}) => {
  const refOfHighlight = useRef();

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
      className="autoCocktailWords"
      onMouseOver={onMouseOver}
      onMouseLeave={onMouseLeave}
      onMouseDown={onMouseDown}
    >
      <ScrollFocus refOfHighlight={refOfHighlight} />
      {cocktails.map((cocktail, index) => (
        <AutoCocktailWord
          key={cocktail.id}
          cocktail={cocktail}
          highlight={highlightIndex === index}
          index={index}
          ref={highlightIndex === index ? refOfHighlight : null}
        />
      ))}
    </ul>
  );
};

export default AutoCocktailWords;
