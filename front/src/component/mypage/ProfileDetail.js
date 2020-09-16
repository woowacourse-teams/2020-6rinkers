import React, { useState } from "react";
import Alert from "react-s-alert";
import { useRecoilValue } from "recoil/dist";
import { updateUser } from "../../api";
import ProfileDetailInput from "./ProfileDetailInput";
import { withdraw } from "../../api";
import { ACCESS_TOKEN } from "../../constants";
import { userState } from "../../recoil";

export default ({ user, setCurrentUser }) => {
  const user = useRecoilValue(userState);
  const [name, setName] = useState(user.currentUser.name);

  const { email } = user.currentUser;

  const onChange = (e) => {
    setName(e.target.value);
  };

  const onUpdateUser = () => {
    const data = { name };
    updateUser(data)
      .then(() => {
        window.location.reload();
        Alert.success("회원정보가 수정되었습니다.");
      })
      .catch((error) => {
        Alert.error("수정에 실패하였습니다.");
      });
  };

  const userWithdraw = async (e) => {
    e.preventDefault();
    await withdraw();
    setCurrentUser({ currentUser: null, authenticated: false });
    localStorage.setItem(ACCESS_TOKEN, null);
  };

  return (
    <div className="profile-detail-container">
      <div className="profile-edit-container">
        <ProfileDetailInput
          name="E-mail"
          userKey="email"
          value={email}
          onChange={onChange}
          disabled
        />
        <ProfileDetailInput
          name="이름"
          userKey="name"
          value={name}
          onChange={onChange}
        />
        <div className="profile-submit">
          <div className="update-submit">
            <button type="submit" onClick={onUpdateUser}>
              수정
            </button>
          </div>
        </div>
        <div className="withdrawal">
          <a href="/" onClick={userWithdraw} className="withdrawal-submit">
            회원 탈퇴
          </a>
        </div>
      </div>
    </div>
  );
};
