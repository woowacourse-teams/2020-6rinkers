import React from "react";
import Menu from "./Menu";

const Menubar = () => {
  const menus = [
    {
      id: 1,
      name: "칵테일 검색",
      uri: "a",
    },
    {
      id: 2,
      name: "바 검색",
      uri: "b",
    },
    {
      id: 3,
      name: "취향 검사",
      uri: "c",
    },
  ];

  return (
    <div className="menubar">
      {menus.map((menu) => (
        <Menu menu={menu} key={menu.id} />
      ))}
    </div>
  );
};

export default Menubar;
