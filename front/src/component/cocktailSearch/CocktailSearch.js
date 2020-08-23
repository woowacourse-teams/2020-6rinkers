import React, { useCallback, useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import TagFilterContainer from "./TagFilterContainer";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([]);
  const [tabIndex, setTabIndex] = useState(0);

  const tabs = [
    {
      title: "이름으로\ 검색",
      content: (
        <SearchContainer cocktails={cocktails} setCocktails={setCocktails} />
      ),
    },
    {
      title: "태그로 검색",
      content: (
        <TagFilterContainer cocktails={cocktails} setCocktails={setCocktails} />
      ),
    },
  ];

  useEffect(() => {
    window.history.scrollRestoration = "manual";
  }, []);

  return (
    <div className="cocktailSearchContainer">
      <div className="searchTabContainer">
        {tabs.map((tab, index) => {
          return (
            <button
              className={index === tabIndex ? "clickedTab" : "unclickedTab"}
              data-index={index}
              onClick={(e) =>
                setTabIndex(Number(e.currentTarget.dataset.index))
              }
            >
              {tab.title}
            </button>
          );
        })}
      </div>
      <div>{tabs[tabIndex].content}</div>
      <SearchedCocktails cocktails={cocktails} />
    </div>
  );
};

export default CocktailSearch;
