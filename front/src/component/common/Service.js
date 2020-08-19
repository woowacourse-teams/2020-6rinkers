import React from "react";
import { NavLink } from "react-router-dom";

const Service = () => {
  return (
    <div className="service">
      <NavLink
        to="/cocktails/search"
        className="serviceItem"
        activeClassName="nav-active"
      >
        칵테일 찾기
      </NavLink>
      <NavLink
        to="/bars"
        className="serviceItem"
        activeClassName="navitem-active"
      >
        바 찾기
      </NavLink>
      <NavLink
        to="/recommend"
        className="serviceItem"
        activeClassName="nav-active"
      >
        칵테일 추천 받기
      </NavLink>
    </div>
  );
};

export default Service;
