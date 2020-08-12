import React from "react";

const CocktailItem = ({ cocktail, updateFromSelectedCocktail }) => {
  return (
    <div className="cocktailItem" data-cocktail-id={cocktail.id} onClick={updateFromSelectedCocktail}>
      <img src={cocktail.imageUrl} alt={cocktail.name} />
      {cocktail.name}
    </div>
  );
};

export default CocktailItem;
