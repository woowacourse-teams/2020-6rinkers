import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import queryString from "query-string";
import SearchedCocktails from "./SearchedCocktails";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import TagFilterContainer from "./TagFilterContainer";
import { useSetRecoilState } from "recoil";
import { favoriteState } from "../../recoil";
import { fetchFavoriteCocktailIds } from "../../api";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([]);
  const [tabIndex, setTabIndex] = useState(0);

  const history = useHistory();

  const tabs = [
    {
      title: "Name",
      content: (
        <SearchContainer
          cocktails={cocktails}
          setCocktails={setCocktails}
          role={role}
        />
      ),
    },
    {
      title: "Tags",
      content: (
        <TagFilterContainer
          cocktails={cocktails}
          setCocktails={setCocktails}
          history={history}
          role={role}
        />
      ),
    },
  ];

  const onClickTab = (e) => {
    const clickedTabIndex = Number(e.currentTarget.dataset.index);

    if (clickedTabIndex === tabIndex) {
      return;
    }

    if (clickedTabIndex === 0) {
      history.push("?contain=");
    }

    if (clickedTabIndex === 1) {
      history.push("?tagIds=");
    }

    setTabIndex(clickedTabIndex);
  };

  useEffect(() => {
    const query = queryString.parse(history.location.search);

    if (!history.location.search) {
      history.push("?contain=");
    }

    if ("tagIds" in query) {
      setTabIndex(1);
    }

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
                onClick={onClickTab}
              >
                {tab.title}
              </button>
            );
          })}
        </div>
      </div>
      <div>{tabs[tabIndex].content}</div>
      <SearchedCocktails cocktails={cocktails} setCocktails={setCocktails} />
    </div>
  );
};

export default CocktailSearch;
