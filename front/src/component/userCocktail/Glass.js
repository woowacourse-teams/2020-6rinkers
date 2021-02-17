import React from "react";
import { useHistory } from "react-router-dom";
import { useRecoilState } from "recoil";
import div from "infinite-react-carousel";
import { userCocktailState } from "../../recoil";
import GlassItem from "./GlassItem";

const Glass = ({ setStage }) => {
  const history = useHistory();
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);

  const onNext = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/amount");
    setStage("amount");
  };

  const glasses =
    // api로 받아오든 내부에 있는 값을 가져오든 해야한다.
    [
      { id: 1, name: "반 잔" },
      { id: 2, name: "반의 반 잔" },
      { id: 3, name: "한 잔" },
      { id: 4, name: "두 잔" },
      { id: 5, name: "세 잔" },
      { id: 6, name: "네 잔" },
    ];

  return (
    <div className="glass-container">
      <div>glass 화면입니다.</div>
      <div>
        {"재료: " +
          userCocktail.recipe[userCocktail.recipe.length - 1].ingredientName}
      </div>
      <div>
        {/*Slider로 수정*/}
        {glasses && glasses.map((it) => <GlassItem glass={it} key={it.id} />)}
      </div>
      <button className="next-button" type="submit" onClick={onNext}>
        얼마나 따라야 할까요?
      </button>
    </div>
  );
};

export default Glass;
