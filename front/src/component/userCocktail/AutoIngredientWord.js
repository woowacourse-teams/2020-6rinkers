import React, { forwardRef } from "react";
import { Link } from "react-router-dom";

const AutoIngredientWord = forwardRef(
  ({ ingredient, highlight, index }, ref) => {
    return (
      <div>
        <Link to={`/ingredients/${ingredient.id}`}>
          <li
            className={`auto-ingredient-word ${highlight ? "highlight" : ""}`}
            data-id={ingredient.id}
            data-index={index}
            ref={ref}
          >
            {ingredient.name}
          </li>
        </Link>
      </div>
    );
  }
);

export default AutoIngredientWord;
