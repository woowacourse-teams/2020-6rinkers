import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";
import { fetchAllIngredients } from "../../api";

const Ingredient = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState({ id: 0, name: "기본" });
  const [ingredients, setIngredients] = useState([]);

  const searchIngredient = (e) => {
    // 자동완성 api 쏘고 받아오고 띄워주고. 그니가 구현한 자동완성처럼.
    // 혹은 아래 ingredients를 받아올 때에 모든 ingredients를 다 받아오고 랜덤으로 몇 개만 아래에서 보여주고
    // 여기서 자동완성도 api 안찌르고 미리 받아온거 내에서 처리하기.
  };

  const fetchIngredients = async () => {
    const response = await fetchAllIngredients();
    setIngredients(response.data);
  };

  useEffect(() => {
    fetchIngredients();
  }, []);

  const onNext = (e) => {
    e.preventDefault();
    setUserCocktail({
      ...userCocktail,
      userRecipeItemRequests: [
        ...userCocktail.userRecipeItemRequests,
        { ingredientId: selected.id, ingredientName: selected.name },
      ],
    });
    history.push("/my-cocktail/quantity-unit");
    setStage("quantity-unit");
  };

  const onSelect = (e) => {
    const selectedId = e.target.dataset.id;
    const found = ingredients.find((it) => it.id === parseInt(selectedId));
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
        {ingredients &&
          ingredients.map((it, index) => {
            return (
              <div
                key={"ingredient" + index}
                data-id={it.id}
                onClick={onSelect}
              >
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
