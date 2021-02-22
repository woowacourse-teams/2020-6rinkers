import React from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";

const Recipe = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  let description = "";

  const onDescriptionChange = (e) => {
    description = e.target.value;
  };

  const onSave = (e) => {
    e.preventDefault();

    setUserCocktail({
      ...userCocktail,
      description: description,
    });

    history.push("/my-cocktail");
    setStage("");
  };

  const onIngredient = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/ingredients");
    setStage("ingredients");
  };

  const removeRecipe = (e) => {
    if (!window.confirm("정말 레시피를 제거하시겠습니까?")) {
      return;
    }
    const selectedIndex = parseInt(e.target.dataset.id);
    const recipeRequests = [...userCocktail.userRecipeItemRequests];
    recipeRequests.splice(selectedIndex, 1);

    setUserCocktail({
      ...userCocktail,
      userRecipeItemRequests: recipeRequests,
    });
  };

  const recipe = userCocktail.userRecipeItemRequests.map((it, index) => (
    <div key={"recipe" + index}>
      {it.ingredientName} {it.glassName} {it.amountName}
      <button onClick={removeRecipe} data-id={index}>
        X
      </button>
    </div>
  ));

  return (
    <>
      <div>recipe 화면입니다.</div>
      {recipe}
      <button
        className="ingredient-button"
        type="submit"
        onClick={onIngredient}
      >
        재료 추가하기
      </button>
      <input
        type="text"
        placeholder="만든 칵테일에 대해 설명해주세요."
        onChange={onDescriptionChange}
      />
      <button className="next-button" type="submit" onClick={onSave}>
        끝!
      </button>
    </>
  );
};

export default Recipe;
