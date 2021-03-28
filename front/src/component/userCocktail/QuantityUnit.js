import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import "../../css/userCocktail/quantityUnit.css";
import { userCocktailState } from "../../recoil";
import QuantityUnitItem from "./QuantityUnitItem";
import { quantityUnits } from "../const";
import "react-responsive-carousel/lib/styles/carousel.min.css"; // requires a loader
import { Carousel } from "react-responsive-carousel";

const QuantityUnit = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState(quantityUnits[0]);

  const onNext = (e) => {
    e.preventDefault();

    if (selected.id === 0) {
      alert("어떤 잔을 이용하실건가요?");
      return;
    }

    const lastRecipe =
      userCocktail.userRecipeItemRequests[
        userCocktail.userRecipeItemRequests.length - 1
      ];
    setUserCocktail({
      ...userCocktail,
      userRecipeItemRequests: [
        ...userCocktail.userRecipeItemRequests.slice(0, -1),
        {
          ingredientId: lastRecipe.ingredientId,
          ingredientName: lastRecipe.ingredientName,
          quantityUnitId: selected.id,
          quantityUnitName: selected.name,
          quantityUnitImagePath: selected.path,
          quantityUnit: selected.quantityUnit,
        },
      ],
    });
    history.push("/my-cocktail/quantity");
    setStage("quantity");
  };

  return (
    <div className="quantity-unit-container">
      <div className="ingredient-name-container">
        {
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].ingredientName}
      </div>
      <Carousel
        infiniteLoop={true}
        showStatus={false}
        onChange={(index) => setSelected(quantityUnits[index])}
      >
        {quantityUnits &&
          quantityUnits.map((it) => (
            <QuantityUnitItem unitItem={it} key={it.id} />
          ))}
      </Carousel>
      <button className="next-stage" type="submit" onClick={onNext}>
        얼마나 따라야 할까요?
      </button>
    </div>
  );
};

export default QuantityUnit;
