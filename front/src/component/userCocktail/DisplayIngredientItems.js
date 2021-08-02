import React from "react";

const DisplayIngredientItems = ({ ingredients, onSelect }) => {
  return (
    <div className="ingredient-items-container">
      {ingredients &&
        ingredients.map((it, index) => {
          return (
            <div
              className="ingredient-item-container"
              key={"ingredient" + index}
              data-id={it.id}
              onClick={onSelect}
              style={{ background: `${it.color}` }}
            >
              <p className="ingredient-item-name"> {it.name}</p>
            </div>
          );
        })}
    </div>
  );
};

export default DisplayIngredientItems;
