import React, { useEffect, useState } from "react";
import ProfileImage from "./ProfileImage";
import ProfileDetail from "./ProfileDetail";
import "../../css/mypage/profile.css";
import { Redirect } from "react-router-dom";
import { USER_PROTOTYPE } from "../../constants";

const MyProfile = ({ currentUser, authenticated }) => {
  const [user, setUser] = useState({});

  const onSubmit = (e) => {
    e.preventDefault();
    alert("회원 정보 수정 기능 개발 중입니다.");
  };

  const onUpdateUser = (name, value) => {
    setUser({
      ...user,
      [name]: value,
    });
  };

  const initUser = async () => {
    setUser({ ...currentUser });
  };

  useEffect(() => {
    initUser();
  }, []);

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
    <div className="profile-container">
      <h2>Profile</h2>
      <ProfileImage user={user} onUpdateUser={onUpdateUser} />
      <ProfileDetail
        user={user}
        onUpdateUser={onUpdateUser}
        onSubmit={onSubmit}
      />
    </div>
  );
};

export default MyProfile;
