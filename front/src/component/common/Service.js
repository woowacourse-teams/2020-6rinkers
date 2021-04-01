import React from "react";
import { NavLink } from "react-router-dom";

const Service = ({ authenticated, currentUser, handleLogout }) => {
  return (
    <div className="service">
      <div className="options">
        <NavLink
          to="/cocktails/search"
          className="service-item"
          activeClassName="nav-active"
        >
          칵테일 찾기
        </NavLink>
        <NavLink
          to="/my-cocktail"
          className="service-item"
          activeClassName="nav-active"
        >
          나만의 칵테일
        </NavLink>
        <NavLink
          to="/recommend"
          className="service-item"
          activeClassName="nav-active"
        >
          칵테일 추천 받기
        </NavLink>
      </div>
      <div className="options">
        {authenticated ? (
          <>
            <NavLink to="/mypage" className="service-item">
              {currentUser.name}
            </NavLink>
            <a onClick={handleLogout} className="service-item">
              로그아웃
            </a>
          </>
        ) : (
          <>
            <NavLink to="/login" className="service-item">
              로그인
            </NavLink>
            <NavLink to="/signup" className="service-item">
              회원가입
            </NavLink>
          </>
        )}
      </div>
    </div>
  );
};

export default Service;
