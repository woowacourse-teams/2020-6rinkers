import React from "react";
import {NavLink} from "react-router-dom";

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
      <div className="serviceItem searchBar">바 찾기</div>
      <NavLink to="/recommend" className="serviceItem" activeClassName="nav-active">
        칵테일 추천 받기
      </NavLink>
    </div>
  );
};

export default Service;
