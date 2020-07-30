import React, { useEffect, useState } from "react";
import { fetchCocktail } from "../../api";
import "../../css/info/detailInfo.css";
import CircularBox from "../common/CircularBox";
import RecipeItems from "./RecipeItems";

const CocktailDetailSearch = ({ match }) => {
  const id = match.params.id;
  const [cocktailData, setCocktailData] = useState({
    cocktail: "",
    tags: [],
    recipe: [],
  });

  const onLoadCocktail = async () => {
    const response = await fetchCocktail(id);
    const data = response.data;
    setCocktailData({
      cocktail: data,
      tags: data.tags,
      recipe: data.recipe,
    });
  };

  useEffect(() => {
    onLoadCocktail(id);
  }, []);

  return (
    <div className="detail-info-container">
      <p className="cocktail-name">{cocktailData.cocktail.name}</p>
      <div className="detail-info-image-container">
        <img
          src={cocktailData.cocktail.imageUrl}
          alt={cocktailData.cocktail.name}
          className="detail-info-image"
        />
      </div>
      <p className="cocktail-abv">도수 : {cocktailData.cocktail.abv}%</p>
      {cocktailData.tags &&
        cocktailData.tags.map((tag, index) => (
          <CircularBox key={"tag" + index} text={tag.name} />
        ))}
      <div>
        {cocktailData.cocktail.sweet ? (
          <CircularBox text="달아요" />
        ) : (
          <CircularBox text="안달아요" />
        )}
        {cocktailData.cocktail.sour ? (
          <CircularBox text="셔요" />
        ) : (
          <CircularBox text="안셔요" />
        )}
        {cocktailData.cocktail.bitter ? (
          <CircularBox text="써요" />
        ) : (
          <CircularBox text="안써요" />
        )}
      </div>
      <table className="cocktail-recipe">
        <thead>
          <tr>
            <th>종류</th>
            <th>양</th>
          </tr>
        </thead>
        <tbody>
          {cocktailData.recipe &&
            cocktailData.recipe.map((item, index) => (
              <RecipeItems item={item} key={"recipeItem" + index} />
            ))}
        </tbody>
      </table>
      <div className="origin">
        <h4>어원</h4>
        <p>{cocktailData.cocktail.origin}</p>
      </div>
      <div className="description">
        <h4>특징</h4>
        <p>{cocktailData.cocktail.description}</p>
      </div>
    </div>
  );
};
export default CocktailDetailSearch;
