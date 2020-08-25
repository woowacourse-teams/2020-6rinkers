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
    return {
      imageUrl:
        "https://avatars2.githubusercontent.com/u/37579660?s=400&u=8c1062c7aad1f67b35bd38ad60ce04fa4cd11a37&v=4",
      email: "qkrtmddhks95@gmail.com",
      name: "그니",
      provider: "local",
    };
  }

  const onSubmit = async (e) => {
    e.preventDefault();
    await updateUser(user);
  };

  const onUpdateUser = (value, name) => {
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
      <ProfileDetail user={user} onUpdateUser={onUpdateUser} />
      <div className="profile-submit">
        <button type="submit" onClick={onSubmit}>
          수정
        </button>
      </div>
    </div>
  );
};

export default Profile;
