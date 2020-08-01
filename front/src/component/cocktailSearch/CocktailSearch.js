import React, { useCallback, useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import { fetchPagedCocktails } from "../../api";

const CocktailSearch = () => {
  const [loading, setLoading] = useState(false);
  const [cocktails, setCocktails] = useState([
    {
      id: 0,
      name: "블루하와이",
      imageUrl: "/image/blue-hawai.png",
    },
  ]);

  const [lastCocktailId, setLastCocktailId] = useState(0);

  const toggleLoading = async () => {
    await setLoading(!loading);
  };

  const onInitialLoadCocktails = async () => {
    const response = await fetchPagedCocktails(lastCocktailId, 15);
    const content = response.data;

    setCocktails(content);
    setLastCocktailId(content[content.length - 1].id);
  };

  const onLoadCocktails = async (size) => {
    if (loading) {
      return;
    }
    const response = await fetchPagedCocktails(lastCocktailId, size);
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

  useEffect(() => {
    window.addEventListener("scroll", infiniteScroll, true);
    return () => window.removeEventListener("scroll", infiniteScroll, true);
  }, [infiniteScroll]);

  useEffect(() => {
    onInitialLoadCocktails();
  }, []);

  return (
    <div className="cocktailSearchContainer">
      <SearchContainer />
      <SearchedCocktails cocktails={cocktails} />
    </div>
  );
};

export default CocktailSearch;
