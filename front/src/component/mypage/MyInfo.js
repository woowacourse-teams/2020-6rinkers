import React from "react";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import { userState } from "../../recoil";

const MyInfo = () => {
  const user = useRecoilValue(userState).currentUser;
  return (
    <div className="my-info-container">
      <h3>내 정보</h3>
      <div className="my-info">
        <div className="user-image">
          <img
            src={user.imageUrl ? user.imageUrl : "/image/default_user.svg"}
            alt={user.name}
          />
        </div>
        <div className="my-info-details">
          <div className="user-email">{user.email}</div>
          <div className="user-name">{user.name}</div>
        </div>
        <div className="info-modify">
          <Link to="/mypage/profile" className="img-link">
            <img src="/image/setting_icon.svg" alt="setting" />
          </Link>
        </div>
      </div>
    </div>
  );
};

export default MyInfo;
