import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { createUserCocktail } from "../../api";
import { USER_COCKTAIL_PROTOTYPE } from "../../constants";

const Recipe = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [description, setDescription] = useState("");

  const onDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const updateDescription = () => {
    if (description === "") {
      alert("어떤 칵테일인지 설명해주세요.");
      return;
    }

    setUserCocktail({
      ...userCocktail,
      description: description,
    });
  };

  useEffect(() => {
    setUserCocktail({
      ...userCocktail,
      description: description,
    });
  }, [description]);

  const onSave = async (e) => {
    e.preventDefault();
    await updateDescription();
    await createUserCocktail(renderToCreateRequest(userCocktail));

    setUserCocktail(USER_COCKTAIL_PROTOTYPE);

    history.push("/my-cocktail");
    setStage("");
  };

  const renderToCreateRequest = (data) => {
    const userRecipeItemRequests = [];
    for (const userRecipeItemRequest of data.userRecipeItemRequests) {
      userRecipeItemRequests.push({
        ingredientId: userRecipeItemRequest.ingredientId,
        quantity: userRecipeItemRequest.quantity,
        quantityUnit: userRecipeItemRequest.quantityUnit,
      });
    }

    return {
      name: data.name,
      description: data.description,
      userRecipeItemRequests: userRecipeItemRequests,
    };
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

  const recipe = userCocktail
    ? userCocktail.userRecipeItemRequests.map((it, index) => (
        <div key={"recipe" + index}>
          {it.ingredientName} {it.quantityUnitName} {it.quantityName}
          <button onClick={removeRecipe} data-id={index}>
            X
          </button>
        </div>
      ))
    : "";

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
      <button className="next-stage" type="submit" onClick={onSave}>
        끝!
      </button>
    </>
  );
};

export default Recipe;
