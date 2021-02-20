import React, { useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";

const Ingredient = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState({ id: 0, name: "기본" });

  const searchIngredient = (e) => {
    // 자동완성 api 쏘고 받아오고 띄워주고. 그니가 구현한 자동완성처럼.
    // 혹은 아래 ingredients를 받아올 때에 모든 ingredients를 다 받아오고 랜덤으로 몇 개만 아래에서 보여주고
    // 여기서 자동완성도 api 안찌르고 미리 받아온거 내에서 처리하기.
  };

  const onNext = (e) => {
    e.preventDefault();
    setUserCocktail({
      ...userCocktail,
      userRecipeItemRequests: [
        ...userCocktail.userRecipeItemRequests,
        { ingredientId: selected.id, ingredientName: selected.name },
      ],
    });
    history.push("/my-cocktail/glass");
    setStage("glass");
  };

  const ingredients = () => {
    // api를 쏴서 재료 리스트를 받아온다.
    return [
      { id: 1, name: "소주" },
      { id: 2, name: "맥주" },
      { id: 3, name: "탕수육" },
      { id: 4, name: "짜장면" },
      { id: 5, name: "민트" },
      { id: 6, name: "게토레이" },
      { id: 7, name: "사이다" },
      { id: 8, name: "생크림" },
      { id: 9, name: "김치찌개" },
      { id: 10, name: "수육" },
    ];
  };

  const onSelect = (e) => {
    const selectedId = e.target.dataset.id;
    const found = ingredients().find((it) => it.id == selectedId);
    setSelected(found);
  };

  return (
    <>
      <div>ingredient 화면입니다.</div>
      <div>
        <div>{selected.name}</div>
        <button>X</button>
      </div>
      <input type="text" placeholder="검색해보세요." />
      <div>
        {ingredients().map((it, index) => {
          return (
            <div key={"ingredient" + index} data-id={it.id} onClick={onSelect}>
              {it.name}
            </div>
          );
        })}
      </div>
      <button className="name-button" type="submit" onClick={onNext}>
        다음 단계로 가보죠
      </button>
    </>
  );
};

export default Ingredient;
