import React from "react";
import "../../css/common/footer.css";

const Footer = (props) => {
  return (
    <div className="footerContainer">
      <div className="linkContainer">
        <div className="link github">
          <a href="https://github.com/woowacourse-teams/2020-6rinkers">
            <img src="/image/github_logo.png" alt="github logo" />
          </a>
        </div>
        <div className="link instagram">
          <a href="https://www.instagram.com/6rinkers/">
            <img src="/image/instagram_logo.png" alt="instagram logo" />
          </a>
        </div>
      </div>
    </div>
  );
};

export default Footer;
