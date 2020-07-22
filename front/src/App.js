import React from "react";
import "./index.css";
import { Route } from "react-router-dom";
import Admin from "./component/Admin";
import Today from "./component/today/Today";

const App = () => {
  return (
    <div>
      <Route exact path="/admin" component={Admin} />
      <Route exact path="/" component={Today} />
    </div>
  );
};

export default App;
