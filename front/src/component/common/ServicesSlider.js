import React from "react";

const ServiceSlider = ({ slider }) => {
  const isActive = slider ? "serviceSlider active" : "serviceSlider";
  return (
    <div className={isActive}>
      <div className="serviceSliderItem">칵테일 찾기</div>
      <div className="serviceSliderItem">바 찾기</div>
      <div className="serviceSliderItem">칵테일 추천 받기</div>
    </div>
  );
};

export default ServiceSlider;
