import React from "react";
import { Link } from "react-router-dom";
import ServiceSlider from "./ServicesSlider";

const MobileNav = ({ offSlider, toggleSlider, slider, handleLogout }) => {
  return (
    <div className="mobileNav">
      <div className="logo-container">
        <Link to="/" className="textLink" onClick={offSlider}>
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
      <div className="sliderToggle" onClick={toggleSlider}>
        {slider ? (
          <img className="xIcon" src="/image/x.svg" alt="x" />
        ) : (
          <img
            className="hamburgerIcon"
            src="/image/hamburger.svg"
            alt="hamburger"
          />
        )}
      </div>
    </div>
  );
};

export default MobileNav;
