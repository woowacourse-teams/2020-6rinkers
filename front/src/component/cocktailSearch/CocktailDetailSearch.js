import React, { useEffect, useState } from "react";
import { fetchCocktail } from "../../api";
import CircularBox from "../common/CircularBox";
import "../../css/cocktailSearch/cocktailDetailSearch.css";
import RecipeItems from "./RecipeItems";

const LIGHT_BLUE = "rgba(28, 30, 170, 0.8)";
const LIGHT_GREEN = "rgba(70, 170, 24, 0.8)";

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
      <div className="tags-container">
        {cocktailData.tags &&
          cocktailData.tags.map((tag, index) => (
            <CircularBox key={"tag" + index} text={tag.name} />
          ))}
      </div>
      <div className="abv-and-taste-container">
        {cocktailData.cocktail.abv && (
          <CircularBox
            text={cocktailData.cocktail.abv + "%"}
            color={LIGHT_BLUE}
          />
        )}
        {cocktailData.cocktail.sweet ? (
          <CircularBox text="달아요" color={LIGHT_GREEN} />
        ) : (
          <CircularBox text="안달아요" color={LIGHT_GREEN} />
        )}
        {cocktailData.cocktail.sour ? (
          <CircularBox text="셔요" color={LIGHT_GREEN} />
        ) : (
          <CircularBox text="안셔요" color={LIGHT_GREEN} />
        )}
        {cocktailData.cocktail.bitter ? (
          <CircularBox text="써요" color={LIGHT_GREEN} />
        ) : (
          <CircularBox text="안써요" color={LIGHT_GREEN} />
        )}
      </div>
      <div className="recipe-container">
        {cocktailData.recipe &&
          cocktailData.recipe.map((item, index) => (
            <RecipeItems item={item} key={"recipeItem" + index} />
          ))}
      </div>
      <div className="text-container">
        <div className="origin">
          <h4>어원</h4>
          <p>{cocktailData.cocktail.origin}</p>
        </div>
        <div className="description">
          <h4>특징</h4>
          <p>{cocktailData.cocktail.description}</p>
        </div>
      </div>
    </div>
  );
};
export default CocktailDetailSearch;
