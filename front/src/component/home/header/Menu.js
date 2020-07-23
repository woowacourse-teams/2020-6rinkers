import React from "react";
import { Link } from "react-router-dom";

const Menu = ({ menu }) => {
  return (
    <div className="menu">
      <Link to={menu.url}><p>{menu.name}</p></Link>
    </div>
  );
};

export default Menu;
