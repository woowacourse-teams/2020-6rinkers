import React, { useState } from "react";
import { Redirect, useLocation } from "react-router-dom";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import TagInput from "./TagInput";
import TagList from "./TagList";
import { userState } from "../../../recoil";

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
  const [tag, setTag] = useState({
    id: "",
    name: "",
    type: "",
  });
  const role = useRecoilValue(userState).currentUser.role;

  const location = useLocation();

  if (role !== "ROLE_ADMIN") {
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
        <TagInput tag={tag} setTag={setTag} />
        <TagList tagName={tag.name} setTag={setTag} />
      </TagContainer>
    </Container>
  );
};

export default TagAdmin;
