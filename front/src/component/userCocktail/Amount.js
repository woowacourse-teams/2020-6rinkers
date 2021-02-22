import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import div from "infinite-react-carousel";
import GlassItem from "./GlassItem";

const Amount = ({ setStage }) => {
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
          glassId: lastRecipe.id,
          glassName: lastRecipe.name,
          amountId: selected.id,
          amountName: selected.name,
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
      { id: 1, name: "반 잔" },
      { id: 2, name: "반의 반 잔" },
      { id: 3, name: "한 잔" },
      { id: 4, name: "두 잔" },
      { id: 5, name: "세 잔" },
      { id: 6, name: "네 잔" },
    ];

  const onSelect = (e) => {
    const selectedId = e.target.dataset.id;
    const found = units.find((it) => it.id === parseInt(selectedId));
    setSelected(found);
  };

  return (
    <div>
      <div>amount 화면입니다.</div>
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
          ].glassName}
      </div>
      <div>
        {/*Slider로 수정*/}
        {units &&
          units.map((it) => (
            <div className="amount-unit-container" onClick={onSelect}>
              <div className="amount-unit" data-id={it.id}>
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

export default Amount;
