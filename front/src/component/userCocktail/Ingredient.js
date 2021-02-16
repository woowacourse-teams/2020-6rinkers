import React from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";

const Ingredient = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/glass");
    setStage("glass");
  };

  return (
    <>
      <div>ingredient 화면입니다.</div>
      <button className="name-button" type="submit" onClick={onNext}>
        다음 단계로 가보죠
      </button>
    </>
  );
};

export default Ingredient;
