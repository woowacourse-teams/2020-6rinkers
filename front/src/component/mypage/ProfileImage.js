import React, { useState } from "react";

export default ({ user, onUpdateUser }) => {
  const [image, setImage] = useState(null);

  const onChange = (e) => {
    e.preventDefault();
  };

  const onUploadProfileImage = () => {};
  return (
    <div className="profile-image-container">
      <h3>내 사진</h3>
      <div className="profile-image">
        <div className="profile-image-box">
          <div className="user-image">
            <img src={user.imageUrl} alt={user.name} />
          </div>
          <div className="profile-image-update">
            <label>
              <img src="/image/camera.svg" alt="upload-image" />
              <input type="file" name="image-upload" onChange={onChange} />
            </label>
          </div>
        </div>
      </div>
    </div>
  );
};
