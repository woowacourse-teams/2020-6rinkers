import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";
import { fetchAllUserCocktails } from "../../api";
import mix from "mix-css-color";

const UserCocktailItems = ({ setStage }) => {
  // todo 사실은 api를 쏴서 보여줘야 한다.
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [userCocktails, setUserCocktails] = useState([]);
  const history = useHistory();

  const fetchUserCocktails = async () => {
    const output = await fetchAllUserCocktails();
    const data = output.data;
    const response = data.userCocktailResponses;
    setUserCocktails(response);
  };

  useEffect(() => {
    fetchUserCocktails();
  }, [userCocktail]);

  const onCreate = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/name");
    setStage("name");
  };

  const mixColor = (cocktail) => {
    const items = cocktail.userRecipeItemResponses;
    let quantity = items[0].quantity;
    let mixedColor = items[0].ingredientColor;
    for (let i = 1; i < items.length; i++) {
      const tempQuantity = (
        (quantity / (quantity + items[i].quantity)) *
        100
      ).toFixed(0);
      mixedColor = mix(
        items[i - 1].ingredientColor,
        items[i].ingredientColor,
        tempQuantity
      );
      quantity = tempQuantity;
    }
    try {
      return `rgba(${mixedColor.rgba[0]}, ${mixedColor.rgba[1]}, ${mixedColor.rgba[2]}, ${mixedColor.rgba[3]})`;
    } catch (err) {
      return "rgba(0,0,0,0)";
    }
  };

  return (
    <>
      {userCocktails &&
        userCocktails.map((it, index) => (
          <div
            key={`user-cocktail-${index}`}
            style={{ backgroundColor: `${mixColor(it)}` }}
          >
            {it.name}
          </div>
        ))}
      <button onClick={onCreate}>나도 만들기</button>
    </>
  );
};

export default UserCocktailItems;
