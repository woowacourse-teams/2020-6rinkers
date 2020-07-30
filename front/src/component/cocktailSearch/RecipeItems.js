import React from "react";

const RecipeItems = ({ item }) => {
  const isRecipeLiquor = (item) => {
    return !isNaN(Number(item.quantity));
  };

  return (
    <>
      <tr>
        <td>{item.ingredient}</td>
        <td>
          {isRecipeLiquor(item) ? (
            <span>{item.quantity}ml</span>
          ) : (
            <span>{item.quantity}</span>
          )}
        </td>
      </tr>
    </>
  );
};

export default RecipeItems;
