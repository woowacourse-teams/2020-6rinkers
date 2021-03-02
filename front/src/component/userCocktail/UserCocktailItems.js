import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";
import { fetchAllUserCocktails } from "../../api";

const UserCocktailItems = ({ setStage }) => {
  // todo 사실은 api를 쏴서 보여줘야 한다.
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [userCocktails, setUserCocktails] = useState([]);
  const history = useHistory();

  const fetchUserCocktails = async () => {
    const response = await fetchAllUserCocktails();
    // todo  추가하기
    // setUserCocktails(response.data);
  };

  useEffect(() => {
    fetchUserCocktails();
  }, [userCocktail]);

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
