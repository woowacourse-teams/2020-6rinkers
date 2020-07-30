import React from "react";
import "../../css/common/circularBox.css";

const CircularBox = ({ text }) => {
  return (
    <>
      <div className="circular-box">{text}</div>
    </>
  );
};

export default CircularBox;
