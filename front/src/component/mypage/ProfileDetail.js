import React from "react";
import ProfileDetailInput from "./ProfileDetailInput";

export default ({ user, onUpdateUser }) => {
  const onChange = (e) => {
    const { value, name } = e.target;
    onUpdateUser(value, name);
  };

  return (
    <div className="profile-detail-container">
      <div className="profile-edit-container">
        <ProfileDetailInput
          name="E-mail"
          value={user.email}
          onChange={onChange}
        />
        <ProfileDetailInput name="이름" value={user.name} onChange={onChange} />
      </div>
    </div>
  );
};
