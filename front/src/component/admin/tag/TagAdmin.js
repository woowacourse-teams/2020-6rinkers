import React, { useState } from "react";
import styled from "styled-components";
import TagInput from "./TagInput";
import TagList from "./TagList";

const Container = styled.div`
  display: flex;
  justify-content: center;
  overflow: hidden;
  width: 100%;
  height: 100%;
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
  const [tagName, setTagName] = useState("");
  const [tagType, setTagType] = useState("");

  return (
    <Container>
      <TagContainer>
        <TagInput
          tagName={tagName}
          tagType={tagType}
          setTagName={setTagName}
          setTagType={setTagType}
        />
        <TagList tagName={tagName} />
      </TagContainer>
    </Container>
  );
};

export default TagAdmin;
