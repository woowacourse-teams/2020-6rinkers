import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import recoilPersist from "recoil-persist";
import ReactGA from "react-ga";
import { RecoilRoot } from "recoil/dist";
import App from "./App";
import "./index.css";

ReactGA.initialize("UA-175294078-1");
ReactGA.pageview(window.location.pathname + window.location.search);

const { RecoilPersist, updateState } = recoilPersist(["userState"], {
  key: "recoil-persist",
  storage: localStorage,
});

ReactDOM.render(
  <BrowserRouter>
    <RecoilRoot
      initializeState={({ set }) => {
        updateState({ set });
      }}
    >
      <RecoilPersist />
      <App />
    </RecoilRoot>
  </BrowserRouter>,
  document.getElementById("root")
);
