import React from "react";
import { Link } from "react-router-dom";
import ServiceSlider from "./ServicesSlider";

const MobileNav = ({ offSlider, toggleSlider, slider, handleLogout }) => {
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
      {slider && (
        <ServiceSlider
          slider={slider}
          toggleSlider={toggleSlider}
          handleLogout={handleLogout}
        />
      )}
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
