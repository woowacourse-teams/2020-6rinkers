import React from "react";
import { NavLink } from "react-router-dom";

const ServiceSlider = ({
  slider,
  toggleSlider,
  authenticated,
  currentUser,
  handleLogout,
}) => {
  const isActive = slider ? "serviceSlider active" : "serviceSlider";

  const closeAndLogout = () => {
    toggleSlider();
    handleLogout();
  };

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
      {authenticated ? (
        <>
          <NavLink
            to="/profile"
            className="serviceSliderItem"
            activeClassName="navitem-active"
            onClick={toggleSlider}
          >
            {currentUser.name}
          </NavLink>
          <a onClick={closeAndLogout} className="serviceSliderItem">
            로그아웃
          </a>
        </>
      ) : (
        <>
          <NavLink
            to="/login"
            className="serviceSliderItem"
            activeClassName="navitem-active"
            onClick={toggleSlider}
          >
            로그인
          </NavLink>
          <NavLink
            to="/signup"
            className="serviceSliderItem"
            activeClassName="navitem-active"
            onClick={toggleSlider}
          >
            회원가입
          </NavLink>
        </>
      )}
    </div>
  );
};

export default ServiceSlider;
