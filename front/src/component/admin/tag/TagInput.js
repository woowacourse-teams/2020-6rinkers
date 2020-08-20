import React, { useState } from "react";
import styled from "styled-components";
import { createTag, updateTag, fetchAllTags } from "../../../api";
import dataToTagRequest from "../../../utils/admin/tagConverter";
import "../../../css/admin/tagAdmin.css";

const TagInputContainer = styled.div`
  display: flex;
  justify-content: center;
  padding-bottom: 20px;
  padding-top: 20px;
  width: 100%;
  border-bottom: 1px solid gray;
`;

const Button = styled.button`
  margin-left: 1.2rem;
`;

const TagInput = ({ tagId, tagName, tagType, setTagName, setTagType }) => {
  const onTagNameChange = (e) => {
    setTagName(e.target.value);
  };

  const onTagTypeChange = (e) => {
    setTagType(e.target.value);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    createTag(dataToTagRequest(tagName, tagType));
    setTagName("");
  };

  const onUpdate = (e) => {
    e.preventDefault();
    updateTag(tagId, dataToTagRequest(tagName, tagType));
  };

  const inputStyle = {
    width: 300,
  };

  return (
    <TagInputContainer>
      <input
        className="tagInput"
        value={tagName}
        placeholder="추가할 태그명을 입력해주세요."
        onChange={onTagNameChange}
        style={inputStyle}
      />
      <select value={tagType} onChange={onTagTypeChange}>
        <option value="X">태그 타입 선택</option>
        <option value="ABV">도수</option>
        <option value="INGREDIENT">재료</option>
        <option value="FLAVOR">맛</option>
        <option value="TEXTURE">식감</option>
        <option value="CONCEPT">컨셉</option>
        <option value="DISLIKE">꺼릴만한 것</option>
      </select>
      <Button type="submit" onClick={onSubmit}>
        등록
      </Button>
      <button className="updateButton" type="submit" onClick={onUpdate}>
        수정
      </button>
    </TagInputContainer>
  );
};

export default TagInput;
