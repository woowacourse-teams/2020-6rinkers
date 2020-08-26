import React from "react";
import ProfileDetailInput from "./ProfileDetailInput";

export default ({ user }) => {

  return (
    <div className="profile-detail-container">
      <div className="profile-edit-container">
        <ProfileDetailInput
          name="E-mail"
          userKey="email"
          value={user.email}
          onChange=""
        />
        <ProfileDetailInput
          name="이름"
          userKey="name"
          value={user.name}
          onChange=""
        />
        {/*<div className="profile-submit">*/}
        {/*  <div className="update-submit">*/}
        {/*    <button type="submit" onClick={onSubmit}>*/}
        {/*      수정*/}
        {/*    </button>*/}
        {/*  </div>*/}
        {/*</div>*/}
        <div className="withdrawal">
          <a href="#" onClick={() => alert("hi")} className="withdrawal-submit">
            회원 탈퇴
          </a>
        </div>
      </div>
    </div>
  );
};
