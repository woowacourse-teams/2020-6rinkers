import React, { useEffect, useState } from "react";
import { fetchAllTags } from "../../../api/index";
import styled from "styled-components";
import TagItem from "./TagItem";
import TagHeader from "./TagHeader";

const TagListContainer = styled.div`
  width: 100%;
  overflow: auto !important;
`;

const TagList = (tag) => {
  const [tags, setTags] = useState([]);

  const onLoadTags = async () => {
    const response = await fetchAllTags();
    setTags(response.data);
  };

  useEffect(() => {
    onLoadTags();
  }, [tag]);

  return (
    <TagListContainer>
      <TagHeader />
      {tags.map((tag, index) => (
        <TagItem tag={tag} key={index} />
      ))}
    </TagListContainer>
  );
};

export default TagList;
