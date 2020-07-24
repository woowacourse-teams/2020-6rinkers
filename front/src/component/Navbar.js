import React from "react";
import { Link } from "react-router-dom";
import "../css/Navbar.css";

const Navbar = () => {
  return (
    <div className="navbar">
      <ul className="navbar">
        <li>
          <Link to="/">
            <li className="nav logo">Cocktail Pick</li>
          </Link>
        </li>
        <li className="nav">칵테일 찾기</li>
        <li className="nav">바 찾기</li>
        <li className="nav">칵테일 취향 찾기</li>
      </ul>
    </div>
  );
};

export default Navbar;
