import React, { useCallback, useEffect, useRef, useState } from "react";
import { fetchAllTags, fetchPagedCocktailsFilteredByTags } from "../../api";

const TagFilterContainer = ({ cocktails, setCocktails }) => {
  const [allTags, setAllTags] = useState([]);
  const [selectedTagIds, setSelectedTagIds] = useState([]);
  const tagFilterContainerRef = useRef(null);
  const tagSelectButtonRef = useRef(null);

  const initAllTags = async () => {
    const response = await fetchAllTags();
    setAllTags(response.data);
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

  const loadCocktails = async (size) => {
    const response = await fetchPagedCocktailsFilteredByTags({
      tagIds: selectedTagIds.join(","),
      id: cocktails.length === 0 ? 0 : cocktails.slice(-1).pop().id,
      size,
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

  const onClickTag = (e) => {
    const tagId = e.target.dataset.id;

    if (selectedTagIds.includes(tagId)) {
      e.target.classList.remove("selectedTag");
      setSelectedTagIds(selectedTagIds.filter((id) => id !== tagId));
      return;
    }

    e.target.classList.add("selectedTag");
    setSelectedTagIds(selectedTagIds.concat([tagId]));
  };

  const onTagSelectButtonClick = () => {
    console.log(tagFilterContainerRef.current.style.display);
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
    window.addEventListener("scroll", infiniteScroll, true);
    return () => window.removeEventListener("scroll", infiniteScroll, true);
  }, [infiniteScroll]);

  useEffect(() => {
    initAllTags();
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
              className="filterTag"
              key={index}
              data-id={tag.tagId}
              onClick={onClickTag}
            >
              {tag.name}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default TagFilterContainer;
