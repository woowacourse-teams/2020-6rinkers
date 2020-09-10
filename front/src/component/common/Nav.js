import React from "react";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import Service from "./Service";
import { userState } from "../../recoil";

const Nav = ({ handleLogout }) => {
  const { authenticated, currentUser } = useRecoilValue(userState);
  return (
    <div className="nav">
      <div className="logo-container">
        <Link to="/" className="textLink">
          <img
            className="logo"
            src="/image/logo/CocktailPick_logo_FullName_transparent.png"
            alt="logo"
          />
        </Link>
      </div>
      <Service
        authenticated={authenticated}
        currentUser={currentUser}
        handleLogout={handleLogout}
      />
    </div>
  );
};
export default Nav;
