import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import div from "infinite-react-carousel";
import QuantitySelector from "./QuantitySelector";

const Quantity = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [quantity, setQuantity] = useState(0.0);

  const updateQuantity = (data) => {
    setQuantity(data);
  };

  const onNext = (e) => {
    e.preventDefault();

    if (quantity <= 0.0) {
      alert("0을 초과하는 값을 입력해주세요!");
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
          quantityUnitImagePath: lastRecipe.path,
          quantityUnit: lastRecipe.quantityUnit,
          quantity: quantity,
        },
      ],
    });
    history.push("/my-cocktail/recipe");
    setStage("recipe");
  };

  return (
    <div>
      <div>
        {"재료: " +
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].ingredientName}
      </div>
      <QuantitySelector
        updateQuantity={updateQuantity}
        imagePath={
          userCocktail.userRecipeItemRequests[
            userCocktail.userRecipeItemRequests.length - 1
          ].quantityUnitImagePath
        }
      />
      <button className="next-stage" type="submit" onClick={onNext}>
        레시피 확인하러 고!
      </button>
    </div>
  );
};

export default Quantity;
