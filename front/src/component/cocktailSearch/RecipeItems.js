import React from "react";
import CircularBox from "../common/CircularBox";
import {GOLD} from "../../constants/Color";


const RecipeItems = ({ item }) => {
  const isRecipeLiquor = (item) => {
    return !isNaN(Number(item.quantity));
  };

  const ingredient = item.ingredient;
  const quantity = isRecipeLiquor(item) ? `${item.quantity}ml` : item.quantity;

  const result = ingredient + " / " + quantity;

  return <CircularBox text={result} color={GOLD} />;
};

export default RecipeItems;
