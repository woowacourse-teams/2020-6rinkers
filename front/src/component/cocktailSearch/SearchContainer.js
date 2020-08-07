import React, {useRef, useState} from "react";
import {Redirect} from "react-router-dom";
import AutoCocktailWords from "./AutoCocktailWords";
import {fetchCocktailsContaining} from "../../api";
import {DOWN, ENTER, ESC, UP} from "../../constants/keyCode";
import ScrollFocus from "./ScrollFocus";

const SearchContainer = ({onUpdateSearchWord}) => {
  const [cocktails, setCocktails] = useState([]);
  const [highlight, setHighlightIndex] = useState(-1);
  const [redirect, setRedirect] = useState("");
  const [autoBox, setAutoBox] = useState(true);
  const searchInput = useRef();

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
    if (highlight === cocktails.length - 1) {
      return;
    }
    setHighlightIndex(highlight + 1);
  };

  const highlightUp = () => {
    if (isNotFocus(highlight)) {
      return;
    }
    setHighlightIndex(highlight - 1);
  };

  const search = () => {
    if (isNotFocus(highlight)) {
      onUpdateSearchWord(searchInput.current.value);
      setAutoBox(false);
      return;
    }

    if (cocktails.length === 0 || autoBox === false) {
      return;
    }

    setRedirect(`/cocktails/${cocktails[highlight].id}`);
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
      setCocktails([]);
      return;
    }

    const response = await fetchCocktailsContaining(word);
    const autoCompleted = response.data;
    setCocktails(autoCompleted);
    setAutoBox(true);
    highlightOut();
  };

  const onMouseDown = () => search();

  return redirect ? (
    <Redirect push to={redirect}/>
  ) : (
    <div className="searchContainer">
      <ScrollFocus/>
      <div className="search">
        <input
          className="cocktailSearchInput"
          type="text"
          placeholder="검색어를 입력하세요."
          onChange={onChange}
          onKeyDown={onKeyDown}
          onMouseDown={highlightOut}
          onBlur={onBlur}
          ref={searchInput}
        />
        {!autoBox || (
          <AutoCocktailWords
            cocktails={cocktails}
            highlight={highlight}
            updateHighlight={setHighlightIndex}
          />
        )}
        <div className="searchButtonContainer" onMouseDown={onMouseDown}>
          <img className="searchButton" src="/image/search.svg" alt="search"/>
        </div>
      </div>
    </div>
  );
};

export default SearchContainer;
