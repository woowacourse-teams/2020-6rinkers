import React, { useEffect, useState } from "react";
import { fetchAllTags } from "../../../api/index";
import styled from "styled-components";
import TagItem from "./TagItem";

const TagListContainer = styled.div`
  width: 100%;
  overflow: auto !important;
`;

const TagList = (tag) => {
  const [tags, setTags] = useState([]);

  const onLoadTags = async () => {
    const response = await fetchAllTags();
    const content = response.data;
    setTags(content);
  };

  useEffect(() => {
    onLoadTags();
  }, [tag]);

  return (
    <TagListContainer>
      {tags.map((tag, index) => (
        <TagItem tag={tag.name} key={index} />
      ))}
    </TagListContainer>
  );
};

export default TagList;
