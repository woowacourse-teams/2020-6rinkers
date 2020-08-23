import React, { useCallback, useEffect, useRef, useState } from "react";
import { Redirect } from "react-router-dom";
import AutoCocktailWords from "./AutoCocktailWords";
import {
  fetchCocktailsContaining,
  fetchPagedCocktailsContainingWord,
} from "../../api";
import { DOWN, ENTER, ESC, UP } from "../../constants/keyCode";

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
    const response = await fetchPagedCocktailsContainingWord({
      contain: searchWord,
      id: 0,
      size: 15,
    });
    const content = response.data;

    setCocktails(content);
  };

  const loadCocktails = async (size) => {
    const response = await fetchPagedCocktailsContainingWord({
      contain: searchWord,
      id: cocktails.length === 0 ? 0 : cocktails.slice(-1).pop().id,
      size: size,
    });

    const content = response.data;
    if (content.length === 0) {
      return;
    }

    await setCocktails(cocktails.concat(content));
  };

  const infiniteScroll = useCallback(async () => {
    const size = window.innerWidth > 700 ? 18 : 6;
    const threshold = window.innerWidth > 700 ? 1600 : 1300;

    if (
      document.documentElement.scrollTop +
        document.documentElement.clientHeight >=
      document.documentElement.scrollHeight - threshold
    ) {
      window.removeEventListener("scroll", infiniteScroll, true);
      await loadCocktails(size);
      window.addEventListener("scroll", infiniteScroll, true);
    }
  }, [cocktails]);

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

    const response = await fetchCocktailsContaining(word);
    const autoCompleted = response.data;
    setAutoCompletedCocktails(autoCompleted);
    setAutoBox(true);
    highlightOut();
  };

  useEffect(() => {
    window.addEventListener("scroll", infiniteScroll, true);
    return () => window.removeEventListener("scroll", infiniteScroll, true);
  }, [infiniteScroll]);

  useEffect(() => {
    fetchCocktails();
  }, [searchWord]);

  return redirect ? (
    <Redirect push to={redirect} />
  ) : (
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
          <img className="searchButton" src="/image/search.svg" alt="search" />
        </div>
      </div>
    </div>
  );
};

export default SearchContainer;
