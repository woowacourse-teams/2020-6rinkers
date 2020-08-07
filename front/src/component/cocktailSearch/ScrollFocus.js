import React, {useEffect} from "react";

const ScrollFocus = () => {
  useEffect(() => {
    const highLighted = document.getElementsByClassName("highlight")[0];
    if (!highLighted) {
      return;
    }

    highLighted.scrollIntoView({ block: "nearest" });
  });

  return <></>;
};

export default ScrollFocus;
