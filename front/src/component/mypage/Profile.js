import React, { useEffect, useState } from "react";
import ProfileImage from "./ProfileImage";
import ProfileDetail from "./ProfileDetail";
import "../../css/mypage/profile.css";

const Profile = () => {
  const [user, setUser] = useState({});
  async function fetchUser() {
    return {
      imageUrl:
        "https://avatars2.githubusercontent.com/u/37579660?s=400&u=8c1062c7aad1f67b35bd38ad60ce04fa4cd11a37&v=4",
      email: "qkrtmddhks95@gmail.com",
      name: "토니",
      provider: "local",
    };
  }

  async function updateUser() {
    return {};
  }

  const onSubmit = async (e) => {
    e.preventDefault();
    await updateUser(user);
    alert("공사중입니다.");
  };

  const onUpdateUser = (name, value) => {
    setUser({
      ...user,
      [name]: value,
    });
  };

  const initUser = async () => {
    setUser(await fetchUser());
  };

  useEffect(() => {
    initUser();
  }, []);

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

export default Profile;
