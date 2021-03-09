import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import "../../css/userCocktail/userRecipe.css";
import { createUserCocktail } from "../../api";
import { USER_COCKTAIL_PROTOTYPE } from "../../constants";
import UserRecipeItem from "./UserRecipeItem";

const UserRecipe = ({ setStage }) => {
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

  return (
    <div className="user-recipe-container">
      <UserRecipeItem userCocktail={userCocktail} removeRecipe={removeRecipe} />
      <button className="next-stage" type="submit" onClick={onIngredient}>
        재료를 더 추가할래요!
      </button>
      <textarea
        className="description-textarea-container"
        placeholder="만든 칵테일에 대해 설명해주세요."
        onChange={onDescriptionChange}
      />
      <button className="next-stage" type="submit" onClick={onSave}>
        저장하고 확인하러가기!
      </button>
    </div>
  );
};

export default UserRecipe;
