import React, { useEffect, useState } from "react";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import TagFilterContainer from "./TagFilterContainer";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([]);
  const [tabIndex, setTabIndex] = useState(0);

  const tabs = [
    {
      title: "Name",
      content: (
        <SearchContainer cocktails={cocktails} setCocktails={setCocktails} />
      ),
    },
    {
      title: "Tags",
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
      <div className="searchTabContainerBox">
        <div className="searchTabContainer">
          {tabs.map((tab, index) => {
            return (
              <button
                className={index === tabIndex ? "clickedTab" : "unclickedTab"}
                key={index}
                data-index={index}
                onClick={(e) =>
                  setTabIndex(Number(e.currentTarget.dataset.index))}
              >
                {tab.title}
              </button>
            );
          })}
        </div>
      </div>
      <div>{tabs[tabIndex].content}</div>
      <SearchedCocktails cocktails={cocktails} />
    </div>
  );
};

export default CocktailSearch;
