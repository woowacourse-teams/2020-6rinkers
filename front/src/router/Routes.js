import React from "react";
import { Route, Switch } from "react-router-dom";
import Header from "../component/common/Header";
import Home from "../component/home/Home";
import Footer from "../component/common/Footer";
import AdminHeader from "../component/admin/AdminHeader";
import Admin from "../component/admin/Admin";

const Routes = () => {
  return (
    <Switch>
      <Route exact path="/">
        <Header />
        <Home />
        <Footer />
      </Route>
      <Route path="/admin">
        <AdminHeader />
        <Admin />
      </Route>
    </Switch>
  );
};

export default Routes;
