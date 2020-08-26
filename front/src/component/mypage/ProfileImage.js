import React from "react";

export default ({ user }) => {
  const onChange = (e) => {
    e.preventDefault();
  };

  return (
    <div className="profile-image-container">
      <h3>내 정보</h3>
      <div className="profile-image">
        <div className="profile-image-box">
          <div className="user-image">
            <img
              src={user.imageUrl ? user.imageUrl : "/image/default_user.svg"}
              alt={user.name}
            />
          </div>
          {/*<div className="profile-image-update">*/}
          {/*  <label>*/}
          {/*    <img src="/image/camera.svg" alt="upload-image" />*/}
          {/*    <input type="file" name="image-upload" onChange={onChange} />*/}
          {/*  </label>*/}
          {/*</div>*/}
        </div>
      </div>
    </div>
  );
};
