import React from "react";
import "../../css/common/circularBox.css";

const CircularBox = ({ text, color }) => {
  return (
    <>
      <div className="circular-box" style={{backgroundColor: color}}>{text}</div>
    </>
  );
};

export default CircularBox;
