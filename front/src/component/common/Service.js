import React from "react";

const Service = (props) => {
  return (
    <div className="service">
      <div className="serviceItem searchCocktail">칵테일 찾기</div>
      <div className="divider"></div>
      <div className="serviceItem searchBar">바 찾기</div>
      <div className="divider"></div>
      <div className="serviceItem recommendation">칵테일 추천 받기</div>
    </div>
  );
};

export default Service;
