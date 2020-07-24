import React from "react";
import "./index.css";
import { Route } from "react-router-dom";
import Admin from "./pages/Admin";
import Main from "./pages/Main";

const App = () => {
  return (
    <div className="App">
      <Route path="/" exact component={Main} />
      <Route path="/admin" component={Admin} />
    </div>
  );
};

export default App;
