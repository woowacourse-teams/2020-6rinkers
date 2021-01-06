import React, { useEffect } from "react";
import "./index.css";
import Alert from "react-s-alert";
import Routes from "./router/Routes";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";

const App = () => {
  useEffect(() => {
    window.Kakao.init(`${process.env.REACT_APP_KAKAO_KEY}`);
  }, []);
  return (
    <div className="App">
      <Routes />
      <Alert
        stack={{ limit: 3 }}
        timeout={3000}
        position="top-right"
        effect="slide"
        offset={65}
      />
    </div>
  );
};

export default App;
