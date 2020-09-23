import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import queryString from "query-string";
import "../../css/cocktailSearch/cocktailSearch.css";
import SearchContainer from "./SearchContainer";
import TagFilterContainer from "./TagFilterContainer";
import { favoriteClickInduceAlert } from "../alert/Alerts";
import { useRecoilValue } from "recoil";
import { userState } from "../../recoil";

const CocktailSearch = () => {
  const [cocktails, setCocktails] = useState([]);
  const [tabIndex, setTabIndex] = useState(0);
  const user = useRecoilValue(userState);

  const history = useHistory();

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
        <TagFilterContainer
          cocktails={cocktails}
          setCocktails={setCocktails}
          history={history}
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
    <div className="cocktail-search-container">
      {favoriteClickInduceAlert(user.authenticated)}
      <div className="search-tab-container-box">
        <div className="search-tab-container">
          {tabs.map((tab, index) => {
            return (
              <button
                className={index === tabIndex ? "clicked-tab" : "unclicked-tab"}
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
    </div>
  );
};

export default CocktailSearch;
