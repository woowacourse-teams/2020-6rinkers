import React from "react";

const CocktailItem = ({
  cocktail,
  updateFromSelectedCocktail,
  onDeleteCocktail,
}) => {
  return (
    <div
      className="cocktail-item"
      data-cocktail-id={cocktail.id}
      onClick={updateFromSelectedCocktail}
    >
      <img src={cocktail.imageUrl} alt={cocktail.name} />
      {cocktail.name}
      <div className="delete" onClick={(e) => onDeleteCocktail(cocktail.id, e)}>
        삭제
      </div>
    </div>
  );
};

export default CocktailItem;
