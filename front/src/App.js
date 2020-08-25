import React, { useEffect, useState } from "react";
import "./index.css";
import Alert from "react-s-alert";
import Routes from "./router/Routes";
import { getCurrentUser } from "./utils/APIUtils";
import { ACCESS_TOKEN, USER_PROTOTYPE } from "./constants";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";

const App = () => {
  const [cocktails, setCocktails] = useState([]);

  return (
    <div className="App">
      <Routes cocktails={cocktails} setCocktails={setCocktails} />
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
