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
  const [tag, setTag] = useState("");

  const updateTag = (value) => {
    setTag(value);
  };

  return (
    <Container>
      <TagContainer>
        <TagInput tag={tag} updateTag={updateTag} />
        <TagList tag={tag} />
      </TagContainer>
    </Container>
  );
};

export default TagAdmin;
