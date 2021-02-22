import React from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";

const UserCocktailItems = ({ setStage }) => {
  // todo 사실은 api를 쏴서 보여줘야 한다.
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const history = useHistory();

  const onCreate = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/name");
    setStage("name");
  };

  return (
    <>
      <div>{userCocktail.name}</div>
      <div>{userCocktail.description}</div>
      <button onClick={onCreate}>나도 만들기</button>
    </>
  );
};

export default UserCocktailItems;
