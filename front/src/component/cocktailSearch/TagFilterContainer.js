import React, { useCallback, useEffect, useState } from "react";
import { fetchAllTags, fetchPagedCocktailsFilteredByTags } from "../../api";

const TagFilterContainer = ({ cocktails, setCocktails }) => {
  const [allTags, setAllTags] = useState([]);
  const [selectedTagIds, setSelectedTagIds] = useState([]);

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
    <div className="tagFilterContainer">
      {allTags.map((tag, index) => {
        return (
          <div
            className="filterTag"
            key={index}
            data-id={tag.id}
            onClick={onClickTag}
          >
            {tag.name}
          </div>
        );
      })}
    </div>
  );
};

export default TagFilterContainer;
