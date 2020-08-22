import React from "react";
import { Route, Switch } from "react-router-dom";
import Header from "../component/common/Header";
import Home from "../component/home/Home";
import Footer from "../component/common/Footer";
import AdminHeader from "../component/admin/common/AdminHeader";
import CocktailAdmin from "../component/admin/cocktail/CocktailAdmin";
import TagAdmin from "../component/admin/tag/TagAdmin";
import CocktailSearch from "../component/cocktailSearch/CocktailSearch";
import CocktailDetailSearch from "../component/cocktailSearch/CocktailDetailSearch";
import Question from "../component/recommend/Question";
import Result from "../component/result/Result";
import Bar from "../component/bar/Bar";

const Routes = ({ cocktails, setCocktails }) => {
  const checkAdminForHeader = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Header />
        </>
      );
    }
    return <AdminHeader />;
  };

  const checkAdminForFooter = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Footer />
        </>
      );
    }
  };

  return (
    <>
      <Route render={({ location }) => checkAdminForHeader(location)} />
      <div className="contentWrapper">
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/admin/cocktails" component={CocktailAdmin} />
          <Route path="/admin/tags" component={TagAdmin} />
          <Route path="/cocktails/search" component={CocktailSearch} />
          <Route path="/bars" component={Bar} />
          <Route path="/cocktails/:id" component={CocktailDetailSearch} />
          <Route path="/recommend">
            <Question cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route path="/result">
            <Result cocktails={cocktails} />
          </Route>
        </Switch>
      </div>
      <Route render={({ location }) => checkAdminForFooter(location)} />
    </>
  );
};

export default Routes;
