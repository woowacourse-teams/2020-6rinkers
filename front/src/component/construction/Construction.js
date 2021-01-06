import React from "react";
import "./Construction.css";
import ConstructionIcon from "./ConstructionIcon.svg";

const Construction = () => (
  <div className="construction-wrapper">
    <div className="construction-icon">
      <img alt="공사중 아이콘" src={ConstructionIcon} />
    </div>
    <h1>칵테일픽은 공사중입니다.</h1>
    <div className="construction-information">
      안녕하세요.
      <br />
      CocktailPick 서비스를 제작한 마시는 여섯들입니다.
      <br />
      저희 팀은 우아한테크코스 2기 교육과정 중 프로젝트를 진행하며 꾸려졌습니다.
      <br /> 우아한테크코스 2기가 2020년 11월 27일 종료됨에 따라 기존 배포
      환경에 변화가 발생하였습니다.
      <br />
      <strong>
        빠른 시일 내로 배포 환경 변경을 마치고 다시 돌아올 것을 약속드립니다.
      </strong>
      <br />
      슬프고 아픈 시기에 칵테일픽 사용해주시는 여러분들과 더불어 모든 사람이
      하루 빨리 행복해졌으면 바랍니다.
      <br />
      CocktailPick 서비스가 정상화되는 대로 인스타그램을 통해 소식
      전해드리겠습니다.
      <br />
      감사합니다.
      <br />
      <h3>-마시는 여섯들 일동-</h3>
    </div>
  </div>
);

export default Construction;
