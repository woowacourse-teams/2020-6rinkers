import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import div from "infinite-react-carousel";

const Quantity = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState({ id: 0, name: "기본값" });

  const onNext = (e) => {
    e.preventDefault();

    if (selected.id === 0) {
      alert("얼마나 넣으실지 선택해주세요!");
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
          quantityUnitId: lastRecipe.quantityUnitId,
          quantityUnitName: lastRecipe.quantityUnitName,
          quantityUnit: lastRecipe.quantityUnit,
          quantityId: selected.id,
          quantityName: selected.name,
          quantity: selected.quantity,
        },
      ],
    });
    history.push("/my-cocktail/recipe");
    setStage("recipe");
  };

  const units =
    // api로 받아오든 내부에 있는 값을 가져오든 해야한다.
    // 값도 바뀌어야 한다. 잔의 종류에 따라 멘트가 다르게 나와야 한다. Piece일 경우에는 개로 표현한다던가.
    [
      { id: 1, name: "반 잔", quantity: 0.5 },
      { id: 2, name: "반의 반 잔", quantity: 0.25 },
      { id: 3, name: "한 잔", quantity: 1.0 },
      { id: 4, name: "두 잔", quantity: 2.0 },
      { id: 5, name: "세 잔", quantity: 3.0 },
      { id: 6, name: "네 잔", quantity: 4.0 },
    ];

  const onSelect = (e) => {
    const selectedId = e.target.dataset.id;
    const found = units.find((it) => it.id === parseInt(selectedId));
    setSelected(found);
  };

  return (
    <div>
      <div>quantity 화면입니다.</div>
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
        {"잔 종류: " +
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].quantityUnitName}
      </div>
      <div>
        {/*Slider로 수정*/}
        {units &&
          units.map((it, index) => (
            <div
              className="quantity-unit-container"
              onClick={onSelect}
              key={"quantity" + index}
            >
              <div className="quantity-unit" data-id={it.id}>
                {it.name}
              </div>
            </div>
          ))}
      </div>
      <button className="next-button" type="submit" onClick={onNext}>
        레시피 확인하러 고!
      </button>
    </div>
  );
};

export default Quantity;
