import React from "react";

const CocktailItem = ({cocktail}) => {
    return (
        <div className="cocktailItem">
            <img src={cocktail.imageUrl} alt={cocktail.name}/>
            {cocktail.name}
        </div>
    );
};

export default CocktailItem;
