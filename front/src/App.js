import React from "react";
import "./index.css";
import { Route } from "react-router-dom";
import Admin from "./component/admin/Admin";
import Home from "./component/home/Home";
import "css"

const App = () => {
  return (
    <>
      <Route exact path="/admin" component={Admin} />
      <Route path="/" component={Home} />
    </>
  );
};

export default App;
