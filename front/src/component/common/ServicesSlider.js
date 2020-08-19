import React from "react";
import { NavLink } from "react-router-dom";

const ServiceSlider = ({ slider, toggleSlider }) => {
  const isActive = slider ? "serviceSlider active" : "serviceSlider";
  return (
    <div className={isActive}>
      <NavLink
        to="/cocktails/search"
        className="serviceSliderItem"
        activeClassName="navitem-active"
        onClick={toggleSlider}
      >
        칵테일 찾기
      </NavLink>
      <NavLink
        to="/bars"
        className="serviceSliderItem"
        activeClassName="navitem-active"
        onClick={toggleSlider}
      >
        바 찾기
      </NavLink>
      <NavLink
        to="/recommend"
        className="serviceSliderItem"
        activeClassName="navitem-active"
        onClick={toggleSlider}
      >
        칵테일 추천 받기
      </NavLink>
    </div>
  );
};

export default ServiceSlider;
