import React from "react";
import { Link, Redirect } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import MyInfo from "./MyInfo";
import "../../css/mypage/myPage.css";
import { userState } from "../../recoil";
import MyFavoritesContainer from "../user/MyFavorites";
import "../../css/user/profile.css";

const MyPage = () => {
  const { authenticated, currentUser } = useRecoilValue(userState);
  if (!authenticated) {
    return (
      <Redirect
        to={{
          pathname: "/login",
        }}
      />
    );
  }
  return (
    <div className="my-page-container">
      <h2>My Page</h2>
      <MyInfo />
      <div className="profile-container">
        <div className="admin-page-wrapper">
          {currentUser.role === "ROLE_ADMIN" ? (
            <Link to="/admin/cocktails">칵테일 관리 페이지</Link>
          ) : (
            <></>
          )}
          {currentUser.role === "ROLE_ADMIN" ? (
            <Link to="/admin/tags">태그 관리 페이지</Link>
          ) : (
            <></>
          )}
        </div>
        <MyFavoritesContainer myFavorites={currentUser.myFavorites} />
      </div>
    </div>
  );
};

export default MyPage;
