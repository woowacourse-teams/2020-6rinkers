import React from "react";
import { Link } from "react-router-dom";

const MobileNav = ({ offSlider, toggleSlider, slider }) => {
  return (
    <div className="mobile-nav">
      <div className="logo-container">
        <Link to="/" className="text-link" onClick={offSlider}>
          <img
            className="logo"
            src="/image/logo/CocktailPick_logo_FullName_transparent.png "
            alt="logo"
          />
        </Link>
      </div>
      <div className="slider-toggle" onClick={toggleSlider}>
        {slider ? (
          <img className="x-icon" src="/image/x.svg" alt="x" />
        ) : (
          <img
            className="hamburger-icon"
            src="/image/hamburger.svg"
            alt="hamburger"
          />
        )}
      </div>
    </div>
  );
};

export default MobileNav;
