import React from "react";
import styled from "styled-components";

const TagListContainer = styled.div`
  display: flex;
  justify-content: space-around;
  border-bottom: 1px solid gray;
`;

const TagName = styled.div`
  text-align: center;
  flex-grow: 1;
  border-right: 1px solid gray;
`;
const TagType = styled.div`
  flex-grow: 1;
  text-align: center;
`;

const TagHeader = () => {
  return (
    <TagListContainer>
      <TagName>태그명</TagName>
      <TagType>태그 타입</TagType>
    </TagListContainer>
  );
};

export default TagHeader;
