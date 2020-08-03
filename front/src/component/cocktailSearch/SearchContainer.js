import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import AutoCocktailWords from "./AutoCocktailWords";
import { fetchCocktailsContaining } from "../../api";
import { UP, DOWN, ENTER } from "../../constants/keyCode";
import ScrollFocus from "./ScrollFocus";

const SearchContainer = () => {
  const [cocktails, setCocktails] = useState([]);
  const [highLight, setHighLight] = useState(0);
  const [redirect, setRedirect] = useState("");

  const focusDown = () => {
    if (highLight === cocktails.length - 1) {
      return;
    }
    setHighLight(highLight + 1);
  };

  const focusUp = () => {
    if (highLight === 0) {
      return;
    }
    setHighLight(highLight - 1);
  };

  const redirectWhenHasInput = () => {
    if (cocktails.length === 0) {
      return;
    }
    setRedirect(`/cocktails/${cocktails[highLight].id}`);
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
    setHighLight(0);
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
        />
        <AutoCocktailWords
          cocktails={cocktails}
          highLight={highLight}
          updateHighLight={setHighLight}
        />
        <div className="searchButtonContainer">
          <img className="searchButton" src="/image/search.svg" alt="search" />
        </div>
      </div>
      <div className="tagsContainer">태그 보여주기 창</div>
    </div>
  );
};

export default SearchContainer;
