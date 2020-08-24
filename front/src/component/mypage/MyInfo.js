import React from "react";

const MyInfo = ({ user }) => {
  return (
    <div className="my-info-container">
      <h3>내정보</h3>
      <div className="my-info">
        <div className="user-image">
          <img src={user.imageUrl} alt={user.name} />
        </div>
        <div className="my-info-details">
          <div className="user-email">{user.email}</div>
          <div className="user-name">{user.name}</div>
        </div>
        <div className="info-modify">
          <img src="/image/setting_icon.svg" alt="setting" />
        </div>
      </div>
    </div>
  );
};

export default MyInfo;
