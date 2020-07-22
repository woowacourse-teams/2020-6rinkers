import React from "react";
import "../../../css/todayHeader.css";
import LeftContainer from "./LeftContainer";
import RightContainer from "./RightContainer";

const Header = () => {
  return (
    <div className="today-header">
      <LeftContainer />
      <RightContainer />
    </div>
  );
};

export default Header;
