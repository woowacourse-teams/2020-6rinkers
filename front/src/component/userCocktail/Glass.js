import React from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";

const Glass = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/amount");
    setStage("amount");
  };

  return (
    <>
      <div>glass 화면입니다.</div>
      <button className="next-button" type="submit" onClick={onNext}>
        얼마나 따라야 할까요?
      </button>
    </>
  );
};

export default Glass;
