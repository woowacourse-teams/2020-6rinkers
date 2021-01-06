import React, { useState } from "react";
import "../../css/common/header.css";
import Nav from "./Nav";
import MobileNav from "./MoblieNav";

const Header = () => {
  const [slider, setSlider] = useState(false);

  const toggleSlider = () => {
    setSlider(!slider);
  };

  const offSlider = () => {
    setSlider(false);
  };

  return (
    <div className="header-container">
      <Nav />
      {slider && <div className="cover" onClick={toggleSlider} />}
      <MobileNav
        offSlider={offSlider}
        toggleSlider={toggleSlider}
        slider={slider}
      />
    </div>
  );
};

export default Header;
