import React from "react";
import { Route, Switch } from "react-router-dom";
import Header from "../component/common/Header";
import Home from "../component/home/Home";
import Footer from "../component/common/Footer";
import AdminHeader from "../component/admin/AdminHeader";
import CocktailAdmin from "../component/admin/cocktail/CocktailAdmin";
import TagAdmin from "../component/admin/tag/TagAdmin";

const Routes = () => {
  const checkAdmin = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Header />
          <Footer />
        </>
      );
    }
    return <AdminHeader />;
  };

  return (
    <>
      <Route render={({ location }) => checkAdmin(location)} />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/admin/cocktails" component={CocktailAdmin} />
        <Route exact path="/admin/tags" component={TagAdmin} />
      </Switch>
    </>
  );
};

export default Routes;
