import React from "react";
import styled from "styled-components";

const TagItemContainer = styled.div`
  padding-top: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid gray;
  width: 100%;
  text-align: center;
  &: hover {
    background-color: powderblue;
  }
`;

const TagItem = ({ tag }) => {
  return <TagItemContainer>{tag}</TagItemContainer>;
};

export default TagItem;
