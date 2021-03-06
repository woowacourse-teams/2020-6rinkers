import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import "../../css/userCocktail/items.css";
import { userCocktailState } from "../../recoil";
import { useHistory } from "react-router-dom";
import { fetchAllUserCocktails } from "../../api";
import mix from "mix-css-color";

const UserCocktailItems = ({ setStage }) => {
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
    return convertToRGBA(mixedColor);
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
      <button className="create-button" onClick={onCreate}>
        <div className="content">나만의 칵테일 만들기 🍹</div>
      </button>
    </>
  );
};

export default UserCocktailItems;
