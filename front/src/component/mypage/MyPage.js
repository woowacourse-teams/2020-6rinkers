import React, { useEffect, useState } from "react";
import MyInfo from "./MyInfo";
import "../../css/mypage/myPage.css";

const MyPage = () => {
  const [user, setUser] = useState({});

  function fetchUser() {
    return {
      imageUrl:
        "https://avatars2.githubusercontent.com/u/37579660?s=400&u=8c1062c7aad1f67b35bd38ad60ce04fa4cd11a37&v=4",
      email: "qkrtmddhks95@gmail.com",
      name: "토니",
    };
  }

  const initUser = () => {
    setUser(fetchUser());
  };

  useEffect(() => initUser(), []);
  return (
    <div className="my-page-container">
      <h2>My Page</h2>
      <MyInfo user={user} />
    </div>
  );
};

export default MyPage;
