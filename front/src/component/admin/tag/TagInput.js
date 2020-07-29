import React from "react";
import styled from "styled-components";
import { createTag, fetchAllTags } from "../../../api";
import dataToTagRequest from "../../../utils/admin/TagConverter";
import "../../../css/admin/tag/tagAdmin.css";

const TagInputContainer = styled.div`
  display: flex;
  justify-content: center;
  padding-bottom: 20px;
  padding-top: 20px;
  width: 100%;
  border-bottom: 1px solid gray;
`;

const TagInput = ({ tag, updateTag }) => {
  const onChange = (e) => {
    updateTag(e.target.value);
  };

  const onEnter = (e) => {
    if (e.key === "Enter") {
      onSubmit();
    }
  };

  const onLoadTags = () => {
    const allTags = fetchAllTags();
  };

  const onSubmit = (e) => {
    createTag(dataToTagRequest(tag));
    onLoadTags();
    updateTag("");
  };

  const inputStyle = {
    width: 300,
  };

  return (
    <TagInputContainer>
      <input
        className="tagInput"
        value={tag}
        placeholder="추가할 태그명을 입력해주세요."
        onChange={onChange}
        onKeyDown={onEnter}
        style={inputStyle}
      />
      <button type="submit" on onClick={onSubmit}>
        등록
      </button>
    </TagInputContainer>
  );
};

export default TagInput;
