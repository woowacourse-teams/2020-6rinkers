import React, { useEffect, useState } from "react";
import { fetchCocktail } from "../../api";
import CircularBox from "../common/CircularBox";
import "../../css/cocktailSearch/cocktailDetailSearch.css";

const CocktailDetailSearch = ({ match }) => {
  const id = match.params.id;
  const [cocktail, setCocktail] = useState("");
  const [tags, setTags] = useState([]);
  const [recipe, setRecipe] = useState([]);

  const onLoadCocktail = async () => {
    const response = await fetchCocktail(id);
    const cocktailData = response.data;
    setCocktail(cocktailData);
    setTags(cocktailData.tags);
    setRecipe(cocktailData.recipe);
  };

  useEffect(() => {
    onLoadCocktail(id);
  }, []);

  return (
    <div className="detail-info-container">
      <p className="cocktail-name">{cocktail.name}</p>
      <div className="detail-info-image-container">
        <img
          src={cocktail.imageUrl}
          alt={cocktail.name}
          className="detail-info-image"
        />
      </div>
      <p className="cocktail-abv">도수 : {cocktail.abv}%</p>
      {tags &&
        tags.map((tag, index) => (
          <CircularBox key={"tag" + index} text={tag.name} />
        ))}
      <div>
        {cocktail.sweet ? (
          <CircularBox text="달아요" />
        ) : (
          <CircularBox text="안달아요" />
        )}
        {cocktail.sour ? (
          <CircularBox text="셔요" />
        ) : (
          <CircularBox text="안셔요" />
        )}
        {cocktail.bitter ? (
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
          {recipe &&
            recipe.map((item, index) => (
              <tr key={"item" + index}>
                <td>{item.ingredient}</td>
                <td>
                  {!isNaN(Number(item.quantity)) ? (
                    <span>{item.quantity}ml</span>
                  ) : (
                    <span>{item.quantity}</span>
                  )}
                </td>
              </tr>
            ))}
        </tbody>
      </table>
      <div className="origin">
        <h4>어원</h4>
        <p>{cocktail.origin}</p>
      </div>
      <div className="description">
        <h4>특징</h4>
        <p>{cocktail.description}</p>
      </div>
    </div>
  );
};
export default CocktailDetailSearch;
