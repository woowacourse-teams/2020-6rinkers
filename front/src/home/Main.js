import React from "react";
import styled from "styled-components";
import cocktailImage from "./image.png";

const MainContainer = styled.div`
  border: 1px solid gray;
  width: 600px;
  height: 630px;
  margin-top: 30px;
  margin-bottom: 30px;
  margin-left: 30%;
`;

const TodayCocktail = styled.div`
  border-bottom: 1px solid gray;
  text-align: center;
  font-size: 20px;
  color: red;
`;

const ImageContainer = styled.div`
  text-align: center;
  padding-top: 30px;
`;

const CocktailImage = styled.img`
  display: inline;
  height: 500px;
  width: 500px;
`;

const CocktailName = styled.div`
  color: red;
  font-size: 30px;
  text-align: center;
`;

const Main = () => {
    return (
        <MainContainer>
            <TodayCocktail>오늘의 칵테일</TodayCocktail>
            <ImageContainer>
                <CocktailImage src={cocktailImage}/>
            </ImageContainer>
            <CocktailName>블루 하와이</CocktailName>
        </MainContainer>
    );
};
export default Main;
