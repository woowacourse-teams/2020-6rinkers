import React, { useEffect, useRef, useState } from "react";
import { Redirect } from "react-router-dom";
import AutoCocktailWords from "./AutoCocktailWords";
import {
  fetchCocktailsContaining,
  fetchPagedCocktailsContainingWord,
} from "../../api";
import { DOWN, ENTER, ESC, UP } from "../../constants";
import SearchedCocktails from "./SearchedCocktails";
import MoreButton from "./MoreButton";
import NoSearchResult from "./NoSearchResult";
import Alert from "react-s-alert";

const SearchContainer = ({ cocktails, setCocktails }) => {
  const [autoCompletedCocktails, setAutoCompletedCocktails] = useState([]);
  const [highlightIndex, setHighlightIndex] = useState(-1);
  const [redirect, setRedirect] = useState("");
  const [autoBox, setAutoBox] = useState(true);
  const [searchWord, setSearchWord] = useState("");
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
    if (highlightIndex === autoCompletedCocktails.length - 1) {
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

  const fetchCocktails = async () => {
    try {
      const response = await fetchPagedCocktailsContainingWord({
        contain: searchWord,
        id: 0,
        size: 15,
      });
      const content = response.data;

      setCocktails(content);
    } catch (e) {
      Alert.error((e && e.message) || "칵테일을 불러오는데 실패했습니다.");
    }
  };

  const search = () => {
    if (isNotFocus(highlightIndex)) {
      setSearchWord(searchInput.current.value);
      setAutoBox(false);
      return;
    }

    if (autoCompletedCocktails.length === 0 || autoBox === false) {
      return;
    }

    setRedirect(`/cocktails/${autoCompletedCocktails[highlightIndex].id}`);
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
      setAutoCompletedCocktails([]);
      return;
    }

    try {
      const response = await fetchCocktailsContaining(word);
      const autoCompleted = response.data;
      setAutoCompletedCocktails(autoCompleted);
      setAutoBox(true);
      highlightOut();
    } catch (e) {
      Alert.error((e && e.message) || "칵테일 자동완성에 실패했습니다.");
    }
  };

  useEffect(() => {
    fetchCocktails();
  }, [searchWord]);

  return redirect ? (
    <Redirect push to={redirect} />
  ) : (
    <div>
      <div className="searchContainer">
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
              cocktails={autoCompletedCocktails}
              highlightIndex={highlightIndex}
              updateHighlight={setHighlightIndex}
              onMouseDown={search}
            />
          )}
          <div className="searchButtonContainer" onMouseDown={search}>
            <img
              className="searchButton"
              src="/image/search.svg"
              alt="search"
            />
          </div>
        </div>
      </div>
      <div className="cocktailSearchContent">
        {cocktails.length === 0 ? <NoSearchResult type="Name" /> : ""}
        <SearchedCocktails cocktails={cocktails} />
        <MoreButton
          searchWord={searchWord}
          cocktails={cocktails}
          setCocktails={setCocktails}
        />
      </div>
    </div>
  );
};

export default SearchContainer;
