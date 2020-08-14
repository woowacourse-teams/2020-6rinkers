import React, { useState } from "react";
import styled from "styled-components";
import { createTag, fetchAllTags } from "../../../api";
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

const TagInput = ({ tagName, tagType, setTagName, setTagType }) => {
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
      <select onChange={onTagTypeChange}>
        <option value="X">태그 타입 선택</option>
        <option value="도수">도수</option>
        <option value="재료">재료</option>
        <option value="맛">맛</option>
        <option value="식감">식감</option>
        <option value="컨셉">컨셉</option>
        <option value="꺼릴만한 것">꺼릴만한 것</option>
      </select>
      <Button type="submit" onClick={onSubmit}>
        등록
      </Button>
    </TagInputContainer>
  );
};

export default TagInput;
