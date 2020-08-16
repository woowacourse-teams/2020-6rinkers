import React from "react";
import styled from "styled-components";

const TagItemContainer = styled.div`
  display: flex;
  border-bottom: 1px solid gray;
  width: 100%;
  text-align: center;
  &: hover {
    background-color: powderblue;
  }
`;

const TagName = styled.div`
  flex-grow: 1;
  width: 10%;
  padding-top: 20px;
  padding-bottom: 20px;
`;

const TagType = styled.div`
  flex-grow: 1;
  padding-top: 20px;
  padding-bottom: 20px;
`;

const TagItem = ({ tag }) => {
  return (
    <TagItemContainer>
      <TagName>{tag.name}</TagName>
      <TagType>{tag.tagType}</TagType>
    </TagItemContainer>
  );
};

export default TagItem;
