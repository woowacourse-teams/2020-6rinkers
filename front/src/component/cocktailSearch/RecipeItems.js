import React from "react";
import CircularBox from "../common/CircularBox";

const LIGHT_PURPLE = "rgba(157, 13, 182, 0.8)";

const RecipeItems = ({ item }) => {
  const isRecipeLiquor = (item) => {
    return !isNaN(Number(item.quantity));
  };

  const ingredient = item.ingredient;
  const quantity = isRecipeLiquor(item) ? `${item.quantity}ml` : item.quantity;

  const result = ingredient + " / " + quantity;

  return <CircularBox text={result} color={LIGHT_PURPLE} />;
};

export default RecipeItems;
