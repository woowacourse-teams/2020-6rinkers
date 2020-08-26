import React, { useEffect, useState } from "react";
import MyInfo from "./MyInfo";
import "../../css/mypage/myPage.css";
import { Redirect } from "react-router-dom";

const MyPage = ({ currentUser, authenticated }) => {
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
      <MyInfo user={currentUser} />
    </div>
  );
};

export default MyPage;
