import React from "react";
import { Route, Switch } from "react-router-dom";
import "./index.css";
import Header from "./component/common/Header";
import Admin from "./component/admin/Admin";
import Home from "./component/home/Home";
import AdminHeader from "./component/admin/AdminHeader";

const App = () => {
  return (
    <div className="App">
      <Switch>
        <Route exact path="/">
          <Header />
          <Home />
        </Route>
        <Route path="/admin">
          <AdminHeader />
          <Admin />
        </Route>
      </Switch>
    </div>
  );
};

export default App;
