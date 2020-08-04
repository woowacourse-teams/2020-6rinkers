import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import AutoCocktailWords from "./AutoCocktailWords";
import { fetchCocktailsContaining } from "../../api";
import { UP, DOWN, ENTER } from "../../constants/keyCode";
import ScrollFocus from "./ScrollFocus";

const SearchContainer = () => {
  const [cocktails, setCocktails] = useState([]);
  const [highLightIndex, setHighLightIndex] = useState(-1);
  const [redirect, setRedirect] = useState("");

  const isNotFocus = (index) => {
    return index === -1;
  };

  const focusOut = () => {
    setHighLightIndex(-1);
  };

  const focusDown = () => {
    if (highLightIndex === cocktails.length - 1) {
      return;
    }
    setHighLightIndex(highLightIndex + 1);
  };

  const focusUp = () => {
    if (isNotFocus(highLightIndex)) {
      return;
    }
    setHighLightIndex(highLightIndex - 1);
  };

  const redirectWhenHasInput = () => {
    if (cocktails.length === 0) {
      return;
    }
    setRedirect(`/cocktails/${cocktails[highLightIndex].id}`);
  };

  const onKeyDown = (e) => {
    if (e.keyCode === DOWN || e.keyCode === UP || e.keyCode === ENTER) {
      e.preventDefault();
    }

    if (e.keyCode === DOWN) {
      focusDown();
      return;
    }

    if (e.keyCode === UP) {
      focusUp();
      return;
    }

    if (e.keyCode === ENTER) {
      redirectWhenHasInput();
    }
  };

  const onChange = async (e) => {
    const word = e.target.value;

    if (word.length === 0) {
      setCocktails([]);
      return;
    }

    const response = await fetchCocktailsContaining(word);
    const autoCompleted = response.data;
    setCocktails(autoCompleted);
    focusOut();
  };

  return redirect ? (
    <Redirect push to={redirect} />
  ) : (
    <div className="searchContainer">
      <ScrollFocus />
      <div className="search">
        <input
          className="cocktailSearchInput"
          type="text"
          placeholder="검색어를 입력하세요."
          onChange={onChange}
          onKeyDown={onKeyDown}
          onMouseDown={focusOut}
        />
        <AutoCocktailWords
          cocktails={cocktails}
          highLight={highLightIndex}
          updateHighLight={setHighLightIndex}
        />
        <div className="searchButtonContainer">
          <img className="searchButton" src="/image/search.svg" alt="search" />
        </div>
      </div>
    </div>
  );
};

export default SearchContainer;
