import React from "react";
import { Link } from "react-router-dom";
import Service from "./Service";

const Nav = (props) => {
  return (
    <div className="nav">
      <div className="title">
        <Link to="/" className="textLink">
          Cocktail<span className="highlightCharacter">P</span>ick
        </Link>
      </div>
      <Service />
    </div>
  );
};
export default Nav;
