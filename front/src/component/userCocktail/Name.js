import React from "react";
import { useRecoilState } from "recoil";
import "../../css/userCocktail/name.css";
import { useHistory } from "react-router-dom";
import { userCocktailState } from "../../recoil";

const Name = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  let name = "";

  const onNameChange = (e) => {
    name = e.target.value;
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      onNext(e);
    }
  }

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
      <input className="name-input" onChange={onNameChange} onKeyPress={handleKeyPress} type="text" />
      <button className="next-stage" type="submit" onClick={onNext}>
        <p className="content">칵테일 재료를 정해보아요</p>
      </button>
    </div>
  );
};

export default Name;
