import React, { useState } from "react";
import styled from "styled-components";
import TagInput from "./TagInput";
import TagList from "./TagList";

const Container = styled.div`
  display: flex;
  justify-content: center;
  overflow: auto;
`;

const TagContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 50%;
  border-right: 1px solid gray;
  border-left: 1px solid gray;
  margin-bottom: 30px;
  overflow: auto !important;
`;

const TagAdmin = () => {
  const [tagId, setTagId] = useState("");
  const [tagName, setTagName] = useState("");
  const [tagType, setTagType] = useState("");

  return (
    <Container>
      <TagContainer>
        <TagInput
          tagId={tagId}
          tagName={tagName}
          tagType={tagType}
          setTagName={setTagName}
          setTagType={setTagType}
        />
        <TagList
          tagName={tagName}
          setTagId={setTagId}
          setTagName={setTagName}
          setTagType={setTagType}
        />
      </TagContainer>
    </Container>
  );
};

export default TagAdmin;
