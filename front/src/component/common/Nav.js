import React from "react";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import Service from "./Service";
import { userState } from "../../recoil";

const Nav = ({ handleLogout }) => {
  const { authenticated, currentUser } = useRecoilValue(userState);
  return (
    <div className="nav">
      <div className="title">
        <Link to="/" className="textLink">
          Cocktail<span className="highlightCharacter">P</span>ick
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
