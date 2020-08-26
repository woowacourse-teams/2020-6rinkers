import React from "react";
import { Link } from "react-router-dom";
import ServiceSlider from "./ServicesSlider";

const MobileNav = ({
  toggleSlider,
  slider,
  authenticated,
  currentUser,
  handleLogout,
}) => {
  return (
    <div className="mobileNav">
      <div className="title">
        <Link to="/" className="textLink">
          Cocktail<span className="highlightCharacter">P</span>ick
        </Link>
      </div>
      {slider && (
        <ServiceSlider
          slider={slider}
          toggleSlider={toggleSlider}
          authenticated={authenticated}
          currentUser={currentUser}
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
