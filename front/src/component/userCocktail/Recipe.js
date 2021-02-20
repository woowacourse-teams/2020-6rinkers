import React from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";

const Recipe = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/description");
    setStage("description");
  };

  const onIngredient = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/ingredients");
    setStage("ingredients");
  };

  return (
    <>
      <div>recipe 화면입니다.</div>
      <button
        className="ingredient-button"
        type="submit"
        onClick={onIngredient}
      >
        재료 추가하기
      </button>
      <button className="next-button" type="submit" onClick={onNext}>
        끝!
      </button>
    </>
  );
};

export default Recipe;
