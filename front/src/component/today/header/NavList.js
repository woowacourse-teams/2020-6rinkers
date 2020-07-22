import React from "react";
import NavItem from "./NavItem";

const NavList = () => {
  return (
    <ul>
      <NavItem item="칵테일 찾기" />
      <NavItem item="바 찾기" />
      <NavItem item="칵테일 취향 찾기" />
    </ul>
  );
};

export default NavList;
