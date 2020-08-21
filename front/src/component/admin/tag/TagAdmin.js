import React, { useState } from "react";
import { Redirect, useLocation } from "react-router-dom";
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

const TagAdmin = ({ role }) => {
  const [tagName, setTagName] = useState("");
  const [tagType, setTagType] = useState("");

  const location = useLocation();
  if (!role) {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  } else if (role !== "ROLE_ADMIN") {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  }

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
