import React from "react";
import { Redirect } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil/dist";
import ProfileImage from "./ProfileImage";
import ProfileDetail from "./ProfileDetail";
import "../../css/mypage/profile.css";
import { userState } from "../../recoil";
import AdminButton from "./AdminButton";

const MyProfile = () => {
  const { currentUser, authenticated } = useRecoilValue(userState);
  const setCurrentUser = useSetRecoilState(userState);

  const onSubmit = (e) => {
    e.preventDefault();
    alert("회원 정보 수정 기능 개발 중입니다.");
  };

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
      <ProfileImage user={currentUser} />
      <ProfileDetail
        user={currentUser}
        setCurrentUser={setCurrentUser}
        onSubmit={onSubmit}
      />
      <AdminButton />
    </div>
  );
};

export default MyProfile;
