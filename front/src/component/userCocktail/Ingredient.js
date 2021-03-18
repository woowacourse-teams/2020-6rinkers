import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import "../../css/userCocktail/ingredient.css";
import { useHistory } from "react-router-dom";
import { fetchAllIngredients } from "../../api";
import DisplayIngredientItems from "./DisplayIngredientItems";

const Ingredient = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState({
    id: 0,
    name: "재료를 선택해주세요.",
  });
  const [ingredients, setIngredients] = useState([]);
  const [shuffled, setShuffled] = useState();
  const TAG_LENGTH_LIMIT = 36;
  const [displayIndex, setDisplayIndex] = useState(0);

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

  const shuffle = (array) => {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
  };

  const displayIngredients = () => {
    const shuffled = shuffle(ingredients);
    let count = 0;
    for (let i = 0; i < shuffled.length; i++) {
      count += shuffled[i].name.length;
      if (count >= TAG_LENGTH_LIMIT) {
        break;
      }
      setDisplayIndex(i);
    }
    return shuffled.slice(0, displayIndex);
  };

  useEffect(() => {
    setShuffled(displayIngredients());
  }, [ingredients, displayIndex]);

  const onNext = (e) => {
    e.preventDefault();

    if (selected.id === 0) {
      alert("재료를 선택해주세요!");
      return;
    }

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
    const selectedId = e.currentTarget.dataset.id;
    const found = ingredients.find((it) => it.id === parseInt(selectedId));
    setSelected(found);
  };

  return (
    <div className="ingredient-container">
      <div className="selected-ingredient-container">
        <div>{selected.name}</div>
      </div>
      <input
        className="ingredient-input"
        type="text"
        placeholder="검색해보세요."
      />
      <DisplayIngredientItems ingredients={shuffled} onSelect={onSelect} />
      <button className="next-stage" type="submit" onClick={onNext}>
        다음 단계로 가보죠
      </button>
    </div>
  );
};

export default Ingredient;
