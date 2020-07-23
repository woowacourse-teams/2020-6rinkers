import React from "react";
import styled from "styled-components";

const FooterContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  border-top: 1px solid gray;
`;

const Foot = styled.div`
  font-size: 1 rem;
  padding-left: 40px;
  padding-top: 20px;
  padding-bottom: 20px;
`;

const FootFriend = styled.div`
  justify-content: flex-end;
  font-size: 20px;
  padding-top: 20px;
  padding-right: 60px;
`;

const Footer = () => {
    return (
        <FooterContainer>
            <Foot>
                Copyright : 6drinkers
                <br/>
                생성일 : 2020.07.22
                <br/>
                author : DooGang
            </Foot>
            <FootFriend>
                이건
                <br/>
                오른쪽
                <br/>
                요소
            </FootFriend>
        </FooterContainer>
    );
};

export default Footer;
