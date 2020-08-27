import React from "react";
import "../../css/common/footer.css";

const Footer = () => {
  const kakaoShare = () => {
    window.Kakao.init(`${process.env.REACT_APP_KAKAO_KEY}`);
    window.Kakao.Link.createScrapButton({
      container: "#kakao-button",
      requestUrl: "https://www.cocktailpick.com",
    });
  };
  return (
    <div className="footerContainer">
      <div className="linkContainer">
        <div className="link github">
          <a href="https://github.com/woowacourse-teams/2020-6rinkers">
            <img src="/image/github_logo.png" alt="github logo" />
          </a>
        </div>
        <div className="link instagram">
          <a href="https://www.instagram.com/cocktail_pick/">
            <img src="/image/instagram_logo.png" alt="instagram logo" />
          </a>
        </div>
        <div className="link kakaotalk">
          <a id="kakao-button" onClick={kakaoShare}>
            <img
              src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
              alt="kakaotalk logo"
            />
          </a>
        </div>
      </div>
    </div>
  );
};

export default Footer;
