import React, { useCallback, useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import { fetchPagedCocktails } from "../../api";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([]);
  const [tabIndex, setTabIndex] = useState(0);
  const [searchWord, setSearchWord] = useState("");

  const initCocktails = async () => {
    const response = await fetchPagedCocktails({
      contain: searchWord,
      id: 0,
      size: 15,
    });
    const content = response.data;

    setCocktails(content);
  };

  const onLoadCocktails = async (size) => {
    const response = await fetchPagedCocktails({
      contain: searchWord,
      id: cocktails.slice(-1).pop().id,
      size: size,
    });

    const content = response.data;
    if (content.length === 0) {
      return false;
    }

    await setCocktails(cocktails.concat(content));
    return true;
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

      const isReceive = await onLoadCocktails(size);
      if (isReceive) {
        window.scrollTo({
          top: document.documentElement.scrollHeight - threshold * 2,
        });
      }
      window.addEventListener("scroll", infiniteScroll, true);
    }
  }, [cocktails]);

  const updateSearchWord = (word) => {
    setSearchWord(word);
  };

  const tabs = [
    {
      title: "이름으로 검색",
      content: <SearchContainer onUpdateSearchWord={updateSearchWord} />,
    },
    {
      title: "태그로 검색",
      content: "태그로 검색한드아아아아아아",
    },
  ];

  useEffect(() => {
    window.addEventListener("scroll", infiniteScroll, true);
    return () => window.removeEventListener("scroll", infiniteScroll, true);
  }, [infiniteScroll]);

  useEffect(() => {
    initCocktails();
  }, [searchWord]);

  return (
    <div className="cocktailSearchContainer">
      <div>
        <button>
          {tabs.map((tab, index) => {
            return (
              <button
                className={index === tabIndex ? "clickedTab" : "unClickedTab"}
                data-index={index}
                onClick={(e) =>
                  setTabIndex(Number(e.currentTarget.dataset.index))
                }
              >
                {tab.title}
              </button>
            );
          })}
        </button>
      </div>
      <div>{tabs[tabIndex].content}</div>
      <SearchedCocktails cocktails={cocktails} />
    </div>
  );
};

export default CocktailSearch;
