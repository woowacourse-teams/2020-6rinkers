import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import "../../css/userCocktail/ingredient.css";
import { useHistory } from "react-router-dom";
import { fetchAllIngredients } from "../../api";
import DisplayIngredientItems from "./DisplayIngredientItems";
import SearchContainer from "./SearchContainer";

const Ingredient = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [selected, setSelected] = useState({
    id: 0,
    name: "재료를 선택해주세요.",
  });
  const [ingredients, setIngredients] = useState([]);
  const [shuffled, setShuffled] = useState();

  const selectIngredient = (e) => {
    setSelected(e);
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
    return shuffled.slice(0, 9);
  };

  useEffect(() => {
    setShuffled(displayIngredients());
  }, [ingredients]);

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
      <div className="ingredient-search-container">
        <div className="search-container">
          <SearchContainer
            ingredients={ingredients}
            selectIngredient={selectIngredient}
          />
        </div>
      </div>
      <DisplayIngredientItems ingredients={shuffled} onSelect={onSelect} />
      <button className="next-stage" type="submit" onClick={onNext}>
        다음 단계로 가보죠
      </button>
    </div>
  );
};

export default Ingredient;
