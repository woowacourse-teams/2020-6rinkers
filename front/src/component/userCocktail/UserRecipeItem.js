import React from "react";

const UserRecipeItem = ({ userCocktail, removeRecipe }) => {
  return (
    <div className="user-recipe-item-container">
      {userCocktail
        ? userCocktail.userRecipeItemRequests.map((it, index) => (
            <div className="user-recipe-item" key={"recipe" + index}>
              {it.ingredientName} {it.quantityUnitName} {it.quantity}{" "}
              {it.quantityUnitId === 5 ? " 개" : " 잔"}
              <button onClick={removeRecipe} data-id={index}>
                X
              </button>
            </div>
          ))
        : ""}
    </div>
  );
};

export default UserRecipeItem;
