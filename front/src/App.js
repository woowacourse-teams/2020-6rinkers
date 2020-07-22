import React from "react";
import { Route, Switch } from "react-router-dom";
import "./index.css";
import Header from "./component/Header";
import Main from "./component/Main";
import Home from "./component/Home";

const App = () => {
  return (
    <div className="App">
      <Header />
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route path="/admin">
          <Main />
        </Route>
      </Switch>
    </div>
  );
};

export default App;
