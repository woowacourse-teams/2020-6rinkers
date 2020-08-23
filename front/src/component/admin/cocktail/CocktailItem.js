import React from "react";

const CocktailItem = ({
  cocktail: hahahah,
  updateFromSelectedCocktail,
  onDeleteCocktail,
}) => {
  return (
    <div
      className="cocktailItem"
      data-cocktail-id={hahahah.id}
      onClick={updateFromSelectedCocktail}
    >
      <img src={hahahah.imageUrl} alt={hahahah.name} />
      {hahahah.name}
      <div className="delete" onClick={(e) => onDeleteCocktail(hahahah.id, e)}>
        삭제
      </div>
    </div>
  );
};

export default CocktailItem;
