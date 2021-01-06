import React from "react";
import { Link } from "react-router-dom";

const Nav = () => {
  return (
    <div className="nav">
      <div className="logo-container">
        <Link to="/" className="text-link">
          <img
            className="logo"
            src="/image/logo/CocktailPick_logo_FullName_transparent.png"
            alt="logo"
          />
        </Link>
      </div>
    </div>
  );
};
export default Nav;
