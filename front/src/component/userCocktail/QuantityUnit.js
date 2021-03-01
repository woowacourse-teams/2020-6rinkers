import React, { useState } from "react";
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
  const [selected, setSelected] = useState({ id: 0, name: "기본값" });

  const onNext = (e) => {
    e.preventDefault();
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
        },
      ],
    });
    history.push("/my-cocktail/quantity");
    setStage("quantity");
  };

  const onSelect = (e) => {
    const selectedId = e.currentTarget.dataset.id;
    const found = quantityUnits.find((it) => it.id === parseInt(selectedId));
    setSelected(found);
  };

  return (
    <div className="quantity-unit-container">
      <div>quantity unit 화면입니다.</div>
      <div>
        <div>{selected.name}</div>
        <button>X</button>
      </div>
      <Carousel infiniteLoop={true} showStatus={false}>
        {quantityUnits &&
          quantityUnits.map((it) => (
            <QuantityUnitItem unitItem={it} key={it.id} onSelect={onSelect} />
          ))}
      </Carousel>
      <div>
        {"재료: " +
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].ingredientName}
      </div>
      <button className="next-button" type="submit" onClick={onNext}>
        얼마나 따라야 할까요?
      </button>
    </div>
  );
};

export default QuantityUnit;
