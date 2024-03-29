import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import "../../css/userCocktail/items.css";
import { userCocktailState } from "../../recoil";
import { Link, useHistory } from "react-router-dom";
import { fetchAllUserCocktails } from "../../api";
import mix from "mix-css-color";
import LiquidFillGauge from "react-liquid-gauge";
import { quantityUnits } from "../const";

const UserCocktailItems = ({ setStage }) => {
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  const [userCocktails, setUserCocktails] = useState([]);
  const history = useHistory();

  const fetchUserCocktails = () => {
    const load = async () => {
      const output = await fetchAllUserCocktails();
      const data = output.data;
      const response = data.userCocktailResponses;
      setUserCocktails(response);
    };
    load();
  };

  useEffect(() => {
    fetchUserCocktails();
  }, []);

  const onCreate = (e) => {
    e.preventDefault();
    history.push("/my-cocktail/name");
    setStage("name");
  };

  const convertToRGBA = (data) => {
    return `rgba(${data.rgba[0]}, ${data.rgba[1]}, ${data.rgba[2]}, ${data.rgba[3]})`;
  };

  const getRealQuantity = (unit) => {
    return unit.quantity * convertQuantityUnitToQuantityMl(unit);
  };

  const convertQuantityUnitToQuantityMl = (unit) => {
    return quantityUnits
      .filter((it) => it.quantityUnit === unit.quantityUnit)
      .map((it) => it.ml);
  };

  const mixColor = (cocktail) => {
    const items = cocktail.userRecipeItemResponses;
    let quantity = getRealQuantity(items[0]);
    let firstColor = items[0].ingredientColor;
    let mixedColor = {};

    if (items.length === 1) {
      return firstColor;
    }

    for (let i = 1; i < items.length; i++) {
      const ratio = (
        (quantity / (quantity + getRealQuantity(items[i]))) *
        100
      ).toFixed(0);
      mixedColor = mix(
        i === 1 ? firstColor : convertToRGBA(mixedColor),
        items[i].ingredientColor,
        ratio
      );

      quantity += items[i].quantity;
    }
    return mixedColor.hex;
  };

  const nameSlicer = (name) => {
    if (name.length > 14) {
      return name.substring(0, 11) + "...";
    }
    return name;
  };

  return (
    <>
      {userCocktails &&
        userCocktails.map((it, index) => (
          <Link
            className="user-cocktail-item"
            to={`/my-cocktails/${it.id}`}
            key={`user-cocktail-${index}`}
          >
            <div className="liquid-cup">
              <LiquidFillGauge
                width={70}
                height={90}
                value={64}
                textRenderer={() => <></>}
                waveAnimation={true}
                waveFrequency={1}
                waveAmplitude={7}
                riseAnimation={true}
                outerRadius={1}
                innerRadius={1}
                waveStyle={{
                  fill: `${mixColor(it)}`,
                }}
              />
            </div>
            <div className="name-container">{nameSlicer(it.name)}</div>
            <div className="dummy-container"></div>
          </Link>
        ))}
      <button className="create-button" onClick={onCreate}>
        <div className="content">나도 만들래요 😀</div>
      </button>
    </>
  );
};

export default UserCocktailItems;
