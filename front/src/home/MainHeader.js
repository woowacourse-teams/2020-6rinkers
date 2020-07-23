import React from "react";
import styled from "styled-components";

const HeaderContainer = styled.div`
  display: flex;
  padding-top: 20px;
  padding-bottom: 20px;
  font-size: 20px;
  border-bottom: 1px solid gray;
`;

const CocktailPick = styled.div`
  display: inline;
  padding-left: 30px;
  padding-right: 40px;
  color: red;
`;

const A = styled.a`
  display: inline;
  padding-left: 20px;
  padding-right: 20px;
  color: black;
  text-decoration: none;
  &:hover {
    color: red;
    background-color: pink;
  }
`;

const MainHeader = () => {
    return (
        <HeaderContainer>
            <CocktailPick>Cocktail Pick</CocktailPick>
            <A href={"https://naver.com"}>칵테일 찾기</A>
            <A href={"https://naver.com"}>바 찾기</A>
            <A href={"https://naver.com"}>칵테일 취향 찾기</A>
        </HeaderContainer>
    );
};

export default MainHeader;
