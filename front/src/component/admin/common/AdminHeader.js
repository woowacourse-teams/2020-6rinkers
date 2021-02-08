import React from "react";
import "../../../css/common/header.css";
import { Link } from "react-router-dom";

const AdminHeader = () => {
  return (
    <div className="admin-header-container">
      <div className="admin-title">칵테일 픽 관리자 페이지</div>
      <Link to="/">홈으로</Link>
      <Link to="/admin/cocktails">칵테일</Link>
      <Link to="/admin/tags">태그</Link>
      <Link to="/admin/ingredients">재료</Link>
      <Link to="/admin/terminologies">용어</Link>
    </div>
  );
};

export default AdminHeader;
