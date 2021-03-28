import React, { useRef, useState } from "react";
import Alert from "react-s-alert";
import { DOWN, ENTER, ESC, UP } from "../../constants";
import AutoIngredientWords from "./AutoIngredientWords";
import "../../css/userCocktail/ingredientSearch.css";

const SearchContainer = ({ ingredients, selectIngredient }) => {
  const [autoCompletedIngredients, setAutoCompletedIngredients] = useState([]);
  const [highlightIndex, setHighlightIndex] = useState(-1);
  const [autoBox, setAutoBox] = useState(true);
  const ingredientSearchInput = useRef();

  const isNotFocus = (index) => {
    return index === -1;
  };

  const onBlur = () => {
    setAutoBox(false);
  };

  const highlightOut = () => {
    setHighlightIndex(-1);
    setAutoBox(true);
  };

  const highlightDown = () => {
    if (highlightIndex === autoCompletedIngredients.length - 1) {
      return;
    }
    setHighlightIndex(highlightIndex + 1);
  };

  const highlightUp = () => {
    if (isNotFocus(highlightIndex)) {
      return;
    }
    setHighlightIndex(highlightIndex - 1);
  };

  const search = async () => {
    if (isNotFocus(highlightIndex)) {
      const found = await ingredients.find(
        (it) => it.name === ingredientSearchInput.current.value
      );
      if (!found) {
        return;
      }
      selectIngredient(found);
      setAutoBox(false);
      return;
    }

    ingredientSearchInput.current.value =
      autoCompletedIngredients[highlightIndex].name;
    selectIngredient(autoCompletedIngredients[highlightIndex]);
    setAutoBox(false);
  };

  const onKeyDown = (e) => {
    if (e.keyCode === DOWN || e.keyCode === UP || e.keyCode === ENTER) {
      e.preventDefault();
    }

    if (e.keyCode === DOWN) {
      highlightDown();
      return;
    }

    if (e.keyCode === UP) {
      highlightUp();
      return;
    }

    if (e.keyCode === ENTER) {
      search();
    }

    if (e.keyCode === ESC) {
      setAutoBox(false);
    }
  };

  const onChange = async (e) => {
    const word = e.target.value;

    if (word.length === 0) {
      setAutoCompletedIngredients([]);
      return;
    }

    try {
      const found = await findIngredientsContain(word);
      setAutoCompletedIngredients(found);
      setAutoBox(true);
      highlightOut();
    } catch (e) {
      Alert.error("칵테일 자동완성에 실패했습니다.");
    }
  };

  const findIngredientsContain = (word) => {
    return ingredients.filter((ingredient) => ingredient.name.includes(word));
  };

  return (
    <div className="search">
      <input
        className="ingredient-search-input"
        type="text"
        placeholder="검색어를 입력하세요."
        onChange={onChange}
        onKeyDown={onKeyDown}
        onMouseDown={highlightOut}
        onBlur={onBlur}
        ref={ingredientSearchInput}
      />
      {!autoBox || (
        <AutoIngredientWords
          ingredients={autoCompletedIngredients}
          highlightIndex={highlightIndex}
          updateHighlight={setHighlightIndex}
          onMouseDown={search}
        />
      )}
      {/*todo 버튼 살릴지 여부 이야기하기*/}
      {/*<div className="search-button-container" onMouseDown={search}>*/}
      {/*  선택*/}
      {/*</div>*/}
    </div>
  );
};

export default SearchContainer;
