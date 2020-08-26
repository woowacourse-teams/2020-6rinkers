import React from "react";
import { Redirect } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import MyInfo from "./MyInfo";
import "../../css/mypage/myPage.css";
import { userState } from "../../recoil";

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
    </div>
  );
};

export default MyPage;
