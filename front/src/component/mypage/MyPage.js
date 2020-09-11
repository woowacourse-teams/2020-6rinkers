import React, { useEffect } from "react";
import { Link, Redirect } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import MyInfo from "./MyInfo";
import "../../css/mypage/myPage.css";
import { userState } from "../../recoil";
import MyFavoritesContainer from "../user/MyFavorites";
import "../../css/user/profile.css";
import { getCurrentUser } from "../../api";

const MyPage = () => {
  const setUser = useSetRecoilState(userState);

  useEffect(() => {
    getCurrentUser().then((response) => {
      setUser({
        currentUser: response.data,
        authenticated: true,
      });
    });
  }, []);
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
