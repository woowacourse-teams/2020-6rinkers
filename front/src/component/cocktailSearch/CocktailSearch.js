import React, { useCallback, useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import { fetchPagedCocktails } from "../../api";

const CocktailSearch = ({ role }) => {
  const [loading, setLoading] = useState(false);
  const [cocktails, setCocktails] = useState([]);
  const [lastCocktailId, setLastCocktailId] = useState(0);
  const [searchWord, setSearchWord] = useState("");

  const toggleLoading = async () => {
    await setLoading(!loading);
  };

  const initCocktails = async () => {
    const response = await fetchPagedCocktails({
      contain: searchWord,
      id: 0,
      size: 15,
    });
    const content = response.data;

    setCocktails(content);
    setLastCocktailId(!content.length || content[content.length - 1].id);
  };

  const onLoadCocktails = async (size) => {
    if (loading) {
      return;
    }

    const response = await fetchPagedCocktails({
      contain: searchWord,
      id: lastCocktailId,
      size: size,
    });

    const content = response.data;
    setCocktails(cocktails.concat(content));
    setLastCocktailId(cocktails[cocktails.length - 1].id);
  };

  const infiniteScroll = useCallback(async () => {
    const size = window.innerWidth > 700 ? 18 : 6;
    const threshold = window.innerWidth > 700 ? 1600 : 1300;

    if (
      document.documentElement.scrollTop +
        document.documentElement.clientHeight >=
      document.documentElement.scrollHeight - threshold
    ) {
      await toggleLoading();
      setLastCocktailId(cocktails[cocktails.length - 1].id);
      await onLoadCocktails(size);
    }
  }, [cocktails, lastCocktailId]);

  const updateSearchWord = (word) => {
    setSearchWord(word);
  };

  useEffect(() => {
    window.addEventListener("scroll", infiniteScroll, true);
    return () => window.removeEventListener("scroll", infiniteScroll, true);
  }, [infiniteScroll]);

  useEffect(() => {
    initCocktails();
  }, [searchWord]);

  return (
    <div className="cocktailSearchContainer">
      <SearchContainer onUpdateSearchWord={updateSearchWord} />
      <SearchedCocktails
        cocktails={cocktails}
        setCocktails={setCocktails}
        role={role}
      />
    </div>
  );
};

export default CocktailSearch;
