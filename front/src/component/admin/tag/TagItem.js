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

const TagItem = ({ tag, setTag }) => {
  const onDeleteClick = (e) => {
    e.preventDefault();
    deleteTag(e.target.dataset.tagId)
      .then(() => alert("성공적으로 삭제했습니다!"))
      .catch((error) => {
        alert(error);
      });
  };

  const onUpdateClick = () => {
    setTag({
      id: tag.tagId,
      name: tag.name,
      type: tag.tagType,
    });
  };

  return (
    <TagItemContainer onClick={onUpdateClick}>
      <TagName>{tag.name}</TagName>
      <TagType>{tag.tagType}</TagType>
      <button data-tag-id={tag.tagId} onClick={onDeleteClick}>
        삭제
      </button>
    </TagItemContainer>
  );
};

export default TagItem;
