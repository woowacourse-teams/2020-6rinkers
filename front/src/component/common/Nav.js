import React from "react";
import Service from "./Service";

const Nav = (props) => {
  return (
    <div className="nav">
      <div className="title">
        Cocktail<span className="highlightCharacter">P</span>ick
      </div>
      <Service />
    </div>
  );
};

export default Nav;
