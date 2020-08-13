import React, {useEffect} from "react";

const ScrollFocus = ({refOfHighlight}) => {
  useEffect(() => {
    const highLighted = refOfHighlight.current;
    if (!highLighted) {
      return;
    }

    highLighted.scrollIntoView({block: "nearest"});
  });

  return <></>;
};

export default ScrollFocus;
