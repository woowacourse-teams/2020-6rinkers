import React from "react";
import Alert from "react-s-alert";
import {
  fetchPagedCocktailsContainingWord,
  fetchPagedCocktailsFilteredByTags,
} from "../../api";
import { isDesktop } from "../../constants";
import {
  desktopCocktailSize,
  mobileCocktailSize,
} from "../../constants/CocktailSearch";

const MoreButton = ({
  searchWord,
  selectedTagIds,
  cocktails,
  setCocktails,
  moreButton,
  setMoreButton,
}) => {
  const loadCocktails = async () => {
    const size = isDesktop() ? desktopCocktailSize : mobileCocktailSize;

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
      setMoreButton(false);
      return;
    }

    content.length < size ? setMoreButton(false) : setMoreButton(true);

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

  return moreButton ? (
    <button className="more-button" onClick={onMoreButtonClick}>
      결과 더 보기
    </button>
  ) : (
    <div className="no-more-result">더 이상 검색 결과가 없습니다.</div>
  );
};

export default MoreButton;
