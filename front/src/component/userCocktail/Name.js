import React from "react";
import { useRecoilState } from "recoil";
import { useHistory } from "react-router-dom";
import { userCocktailState } from "../../recoil";

const Name = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNameChange = (e) => {
    setUserCocktail({
      ...userCocktail,
      name: e.target.value,
    });
  };

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/ingredients");
    setStage("ingredients");
  };

  return (
    <>
      <div>name 화면입니다.</div>
      <input className="name-input" onChange={onNameChange} type="text" />
      <button className="next-button" type="submit" onClick={onNext}>
        칵테일 재료를 정해보아요
      </button>
    </>
  );
};

export default Name;