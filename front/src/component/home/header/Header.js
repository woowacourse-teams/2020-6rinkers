import React from "react";
import Logo from "./Logo";
import Menubar from "./Menubar";
import "../../../css/home/header.css";

const Header = () => {
  return (
    <header>
      <Logo />
      <Menubar />
    </header>
  );
};

export default Header;
