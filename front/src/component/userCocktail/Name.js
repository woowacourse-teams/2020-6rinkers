import React from "react";
import { useRecoilState } from "recoil";
import { useHistory } from "react-router-dom";
import { userCocktailState } from "../../recoil";

const Name = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  let name = "";

  const onNameChange = (e) => {
    name = e.target.value;
  };

  const onNext = (e) => {
    e.preventDefault();

    if (name === "") {
      alert("이름을 입력해주세요!");
      return;
    }

    setUserCocktail({
      ...userCocktail,
      name: name,
    });
    history.push("/my-cocktail/ingredients");
    setStage("ingredients");
  };

  return (
    <div className="name-container">
      <input className="name-input" onChange={onNameChange} type="text" />
      <button className="next-button" type="submit" onClick={onNext}>
        칵테일 재료를 정해보아요
      </button>
    </div>
  );
};

export default Name;
