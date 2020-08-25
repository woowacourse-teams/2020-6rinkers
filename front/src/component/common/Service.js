import React from "react";
import { NavLink } from "react-router-dom";

const Service = ({ authenticated, currentUser, handleLogout }) => {
  return (
    <div className="service">
      <div className="options">
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
      <div className="options">
        {authenticated ? (
          <>
            <NavLink to="/profile" className="serviceItem">
              {currentUser.name}
            </NavLink>
            <a onClick={handleLogout} className="serviceItem">
              로그아웃
            </a>
          </>
        ) : (
          <>
            <NavLink to="/login" className="serviceItem">
              로그인
            </NavLink>
            <NavLink to="/signup" className="serviceItem">
              회원가입
            </NavLink>
          </>
        )}
      </div>
    </div>
  );
};

export default Service;
