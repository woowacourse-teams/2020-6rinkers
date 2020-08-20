import React from "react";
import { deleteTag } from "../../../api";
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

const onClick = (e) => {
  deleteTag(e.target.dataset.tagId);
};

const TagItem = ({ tag }) => {
  return (
    <TagItemContainer>
      <TagName>{tag.name}</TagName>
      <TagType>{tag.tagType}</TagType>
      <button data-tag-id={tag.tagId} onClick={onClick}>
        삭제
      </button>
    </TagItemContainer>
  );
};

export default TagItem;
