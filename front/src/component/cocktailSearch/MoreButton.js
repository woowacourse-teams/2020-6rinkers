import React from "react";
import Alert from "react-s-alert";
import {
  fetchPagedCocktailsContainingWord,
  fetchPagedCocktailsFilteredByTags,
} from "../../api";

const MoreButton = ({
  searchWord,
  selectedTagIds,
  cocktails,
  setCocktails,
}) => {
  const loadCocktails = async () => {
    const size = window.innerWidth > 700 ? 18 : 6;

    let response;

    if (selectedTagIds) {
      response = await fetchPagedCocktailsFilteredByTags({
        tagIds: selectedTagIds.join(","),
        id: cocktails.length === 0 ? 0 : cocktails.slice(-1).pop().id,
        size,
      });
    } else {
      response = await fetchPagedCocktailsContainingWord({
        contain: searchWord,
        id: cocktails.length === 0 ? 0 : cocktails.slice(-1).pop().id,
        size,
      });
    }

    const content = response.data;
    if (content.length === 0) {
      Alert.success("더 이상 검색 결과가 없습니다.");
      return;
    }

    await setCocktails(cocktails.concat(content));
  };

  const onMoreButtonClick = async () => {
    try {
      await loadCocktails();
    } catch (e) {
      Alert.error("추가 칵테일을 불러오는데 실패했습니다.");
    }
  };

  if (cocktails.length === 0) {
    return <></>;
  }

  return (
    <button className="more-button" onClick={onMoreButtonClick}>
      결과 더 보기
    </button>
  );
};

export default MoreButton;
