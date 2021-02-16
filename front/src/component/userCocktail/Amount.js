import React from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";

const Amount = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/recipe");
    setStage("recipe");
  };

  return (
    <div>
      <div>amount 화면입니다.</div>
      <button className="next-button" type="submit" onClick={onNext}>
        레시피 확인하러 고!
      </button>
    </div>
  );
};

export default Amount;
