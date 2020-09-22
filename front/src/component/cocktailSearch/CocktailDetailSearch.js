import React, { useEffect, useState } from "react";
import { fetchCocktail } from "../../api";
import CircularBox from "../common/CircularBox";
import "../../css/cocktailSearch/cocktailDetailSearch.css";
import RecipeItems from "./RecipeItems";
import CocktailFavorite from "./CocktailFavorite";
import { DARK_GREEN, DARK_BLUE } from "../../constants/Color";
import { Link } from "react-router-dom";
import Alert from "react-s-alert";
import { useRecoilValue } from "recoil";
import { userState } from "../../recoil";
import { tagSearchClickInduceAlert } from "../alert/Alerts";

const CocktailDetailSearch = (props) => {
  const { id } = props.match.params;
  const role = useRecoilValue(userState).currentUser.role;
  const [cocktailData, setCocktailData] = useState({
    cocktail: {},
    tags: [],
    recipe: [],
  });

  const onLoadCocktail = async () => {
    try {
      const response = await fetchCocktail(id);
      const { data } = response;
      setCocktailData({
        cocktail: data,
        tags: data.tags,
        recipe: data.recipe,
      });
    } catch (e) {
      Alert.error("칵테일 상세 조회에 실패했습니다.");
    }
  };

  useEffect(() => {
    onLoadCocktail(id);
  }, []);

  return (
    <div className="detail-info-container">
      {tagSearchClickInduceAlert()}
        <p className="detail-cocktail-name">{cocktailData.cocktail.name}</p>
        <div className="detail-favorite-container">
          {role ? (
            <CocktailFavorite cocktailId={cocktailData.cocktail.id} />
          ) : (
            <div />
          )}
        </div>
      <div className="detail-info-image-container">
        <img
          src={cocktailData.cocktail.imageUrl}
          alt={cocktailData.cocktail.name}
          className="detail-info-image"
        />
      </div>
      <div className="detail-tags-container">
        {cocktailData.tags &&
          cocktailData.tags.map((tag, index) => (
            <Link to={`/cocktails/search?tagIds=${tag.tagId}`}>
              <CircularBox key={`tag${index}`} text={tag.name} />
            </Link>
          ))}
      </div>
      <div className="abv-and-taste-container">
        {cocktailData.cocktail.abv >= 0 && (
          <CircularBox
            text={
              cocktailData.cocktail.abv === 0
                ? "무알콜"
                : `${cocktailData.cocktail.abv}%`
            }
            color={DARK_GREEN}
          />
        )}
        {cocktailData.cocktail.sweet ? (
          <CircularBox text="달아요" color={DARK_BLUE} />
        ) : (
          <CircularBox text="안달아요" color={DARK_BLUE} />
        )}
        {cocktailData.cocktail.sour ? (
          <CircularBox text="셔요" color={DARK_BLUE} />
        ) : (
          <CircularBox text="안셔요" color={DARK_BLUE} />
        )}
        {cocktailData.cocktail.bitter ? (
          <CircularBox text="써요" color={DARK_BLUE} />
        ) : (
          <CircularBox text="안써요" color={DARK_BLUE} />
        )}
      </div>
      <div className="recipe-container">
        {cocktailData.recipe &&
          cocktailData.recipe.map((item, index) => (
            <RecipeItems item={item} key={`recipeItem${index}`} />
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
