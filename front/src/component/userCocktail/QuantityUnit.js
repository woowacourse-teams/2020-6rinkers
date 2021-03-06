import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import div from "infinite-react-carousel";
import { userCocktailState } from "../../recoil";
import QuantityUnitItem from "./QuantityUnitItem";

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

  const quantityUnits =
    // api로 받아오든 내부에 있는 값을 가져오든 해야한다.
    // 값도 바뀌어야 한다. 백에서 원하는 SOJU BEER PAPER SHOT PIECE를 가지고 이미지 링크와 한글 이름이 필요하다.
    [
      { id: 1, name: "SOJU" },
      { id: 2, name: "BEER" },
      { id: 3, name: "PAPER" },
      { id: 4, name: "SHOT" },
      { id: 5, name: "PIECE" },
    ];

  const onSelect = (e) => {
    const selectedId = e.target.dataset.id;
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
      <div>
        {"재료: " +
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].ingredientName}
      </div>
      <div>
        {/*Slider로 수정*/}
        {quantityUnits &&
          quantityUnits.map((it) => (
            <QuantityUnitItem unitItem={it} key={it.id} onSelect={onSelect} />
          ))}
      </div>
      <button className="next-button" type="submit" onClick={onNext}>
        얼마나 따라야 할까요?
      </button>
    </div>
  );
};

export default QuantityUnit;
