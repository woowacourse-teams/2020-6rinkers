import React from "react";
import { Link } from "react-router-dom";
import "../../css/user/profile.css";
import MyFavoritesContainer from "./MyFavorites";

const Profile = ({ role, user, loadCurrentlyLoggedInUser }) => {
  return (
    <div className="profile-container">
      <div className="admin-page-wrapper">
        {role === "ROLE_ADMIN" ? (
          <Link to="/admin/cocktails">칵테일 관리 페이지</Link>
        ) : (
          <></>
        )}
        {role === "ROLE_ADMIN" ? (
          <Link to="/admin/tags">태그 관리 페이지</Link>
        ) : (
          <></>
        )}
      </div>
      <div className="user-info-wrapper">내부 공사중입니다...!</div>
      <MyFavoritesContainer
        myFavorites={user.myFavorites}
        loadCurrentlyLoggedInUser={loadCurrentlyLoggedInUser}
      />
    </div>
  );
};

export default Profile;
