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

  const convertToRGBA = (data) => {
    return `rgba(${data.rgba[0]}, ${data.rgba[1]}, ${data.rgba[2]}, ${data.rgba[3]})`;
  };

  const mixColor = (cocktail) => {
    const items = cocktail.userRecipeItemResponses;
    let quantity = items[0].quantity;
    let firstColor = items[0].ingredientColor;
    let mixedColor = {};

    if (items.length === 1) {
      return firstColor;
    }

    for (let i = 1; i < items.length; i++) {
      const ratio = ((quantity / (quantity + items[i].quantity)) * 100).toFixed(
        0
      );
      mixedColor = mix(
        i === 1 ? firstColor : convertToRGBA(mixedColor),
        items[i].ingredientColor,
        ratio
      );

      quantity += items[i].quantity;
    }
    try {
      return convertToRGBA(mixedColor);
    } catch (err) {
      console.log("err: " + err);
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
