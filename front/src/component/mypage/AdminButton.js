import React from "react";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import { userState } from "../../recoil";
import CircularBox from "../common/CircularBox";

const AdminButton = () => {
  const role = useRecoilValue(userState).currentUser.role;

  if (role === "ROLE_ADMIN") {
    return (
      <>
        <Link to="/admin/cocktails">
          <CircularBox text="칵테일 관리 페이지" />
        </Link>
        <Link to="/admin/tags">
          <CircularBox text="태그 관리 페이지" />
        </Link>
      </>
    );
  }
  return <></>;
};

export default AdminButton;
