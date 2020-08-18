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

const Routes = ({ cocktails, setCocktails }) => {
  const checkAdmin = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Header />
        </>
      );
    }
    return <AdminHeader />;
  };

  return (
    <>
      <Route render={({ location }) => checkAdmin(location)} />
      <div className="contentWrapper">
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/admin/cocktails" component={CocktailAdmin} />
          <Route path="/admin/tags" component={TagAdmin} />
          <Route path="/cocktails/search" component={CocktailSearch} />
          <Route path="/cocktails/:id" component={CocktailDetailSearch} />
          <Route path="/recommend">
            <Question cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route path="/result">
            <Result cocktails={cocktails} />
          </Route>
        </Switch>
      </div>
      <Footer />
    </>
  );
};

export default Routes;
