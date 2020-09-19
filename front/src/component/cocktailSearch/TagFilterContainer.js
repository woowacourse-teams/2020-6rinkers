import React, { useEffect, useRef, useState } from "react";
import queryString from "query-string";
import Alert from "react-s-alert";
import { fetchAllTags, fetchPagedCocktailsFilteredByTags } from "../../api";
import SearchedCocktails from "./SearchedCocktails";
import MoreButton from "./MoreButton";
import NoSearchResult from "./NoSearchResult";

const TagFilterContainer = ({ cocktails, setCocktails, history }) => {
  const [allTags, setAllTags] = useState([]);
  const [selectedTagIds, setSelectedTagIds] = useState([]);
  const tagFilterContainerRef = useRef(null);
  const tagSelectButtonRef = useRef(null);

  const initAllTags = async () => {
    const response = await fetchAllTags();
    setAllTags(
      response.data.map((tag) => {
        tag.tagId = String(tag.tagId);
        return tag;
      })
    );
  };

  const fetchCocktails = async () => {
    const response = await fetchPagedCocktailsFilteredByTags({
      tagIds: selectedTagIds.join(","),
      id: 0,
      size: 15,
    });

    const content = response.data;

    setCocktails(content);
  };

  const onClickTag = (e) => {
    const tagId = e.target.dataset.id;

    if (selectedTagIds.includes(tagId)) {
      const afterTagIds = selectedTagIds.filter((id) => id !== tagId);
      setSelectedTagIds(afterTagIds);
      history.push(`?tagIds=${afterTagIds.join(",")}`);
      return;
    }

    const afterTagIds = selectedTagIds.concat([tagId]);
    setSelectedTagIds(afterTagIds);
    history.push(`?tagIds=${afterTagIds.join(",")}`);
  };

  const onTagSelectButtonClick = () => {
    if (
      tagFilterContainerRef.current.style.display === "none" ||
      tagFilterContainerRef.current.style.display === ""
    ) {
      tagFilterContainerRef.current.style.display = "flex";
      tagSelectButtonRef.current.innerText = "태그 접기";
      return;
    }

    tagFilterContainerRef.current.style.display = "none";
    tagSelectButtonRef.current.innerText = "태그 펼치기";
  };

  useEffect(() => {
    initAllTags()
      .then(() => {
        const query = queryString.parse(history.location.search);

        if ("tagIds" in query) {
          const tagIdsFromQuery = query.tagIds.split(",");
          setSelectedTagIds(tagIdsFromQuery.filter((id) => id !== ""));
        }
      })
      .catch((e) => {
        Alert.error((e && e.message) || "태그 목록을 불러오는데 실패했습니다.");
      });
  }, []);

  useEffect(() => {
    fetchCocktails();
  }, [selectedTagIds]);

  return (
    <div>
      <div className="tagSelectButtonContainer">
        <div
          className="tagSelectButton"
          onClick={onTagSelectButtonClick}
          ref={tagSelectButtonRef}
        >
          태그 펼치기
        </div>
      </div>
      <div className="tagFilterContainer" ref={tagFilterContainerRef}>
        {allTags.map((tag, index) => {
          return (
            <div
              className={`filterTag ${
                selectedTagIds.includes(tag.tagId) ? "selectedTag" : ""
              }`}
              key={index}
              data-id={tag.tagId}
              onClick={onClickTag}
            >
              {tag.name}
            </div>
          );
        })}
      </div>
      <div className="cocktailSearchContent">
        {cocktails.length === 0 ? (<NoSearchResult type="Tags" />) : ""}
        <SearchedCocktails cocktails={cocktails} />
        <MoreButton
          selectedTagIds={selectedTagIds}
          cocktails={cocktails}
          setCocktails={setCocktails}
        />
      </div>
    </div>
  );
};

export default TagFilterContainer;
