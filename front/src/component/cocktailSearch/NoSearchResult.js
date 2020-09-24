import React from "react";

const NoSearchResult = ({ type }) => {
  return (
    <div className="no-search-result">
      검색 결과가 없습니다. 😥
      <ul className="no-search-result-description">
        {type === "Name" && (
          <>
            <li>검색어의 철자와 띄어쓰기가 정확한지 확인해주세요.</li>
            <li>
              검색어의 단어 수를 줄이거나, 보다 일반적인 단어 등 다른 검색어를
              입력해보세요.
            </li>
          </>
        )}
        {type === "Tags" && (
          <>
            <li>검색 태그의 수를 줄이거나, 다른 검색 태그를 선택해보세요.</li>
          </>
        )}
      </ul>
    </div>
  );
};

export default NoSearchResult;
