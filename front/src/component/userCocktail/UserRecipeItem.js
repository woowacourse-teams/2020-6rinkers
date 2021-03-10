import React from "react";

const UserRecipeItem = ({ userCocktail, removeRecipe }) => {
  return (
    <div className="user-recipe-item-container">
      {userCocktail
        ? userCocktail.userRecipeItemRequests.map((it, index) => (
            <div className="user-recipe-item" key={"recipe" + index}>
              {it.ingredientName} {it.quantityUnitName} {it.quantity}{" "}
              {it.quantityUnitId === 5 ? " 개" : " 잔"}
              <div className="a" onClick={removeRecipe} data-id={index}>
                <img
                  className="trash-bin-image"
                  src="/image/trash-bin.png"
                  alt="trash-bin"
                />
              </div>
            </div>
          ))
        : ""}
    </div>
  );
};

export default UserRecipeItem;
