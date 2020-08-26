import React, { useState } from "react";
import "../../css/common/header.css";
import Nav from "./Nav";
import MobileNav from "./MoblieNav";

const Header = ({ handleLogout }) => {
  const [slider, setSlider] = useState(false);

  const toggleSlider = () => {
    setSlider(!slider);
  };

  return (
    <div className="headerContainer">
      <Nav handleLogout={handleLogout} />
      {slider && <div className="cover" onClick={toggleSlider} />}
      <MobileNav
        toggleSlider={toggleSlider}
        slider={slider}
        authenticated={authenticated}
        currentUser={currentUser}
        handleLogout={handleLogout}
      />
    </div>
  );
};

export default Header;
