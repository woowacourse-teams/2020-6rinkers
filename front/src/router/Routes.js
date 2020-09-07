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
import MyPage from "../component/mypage/MyPage";
import OAuth2RedirectHandler from "../oauth2/OAuth2RedirectHandler";
import Login from "../component/user/Login";
import Signup from "../component/user/Signup";
import MyProfile from "../component/mypage/MyProfile";
import TerminologyAdmin from "../component/admin/terminology/TerminologyAdmin";

const Routes = ({ cocktails, setCocktails, handleLogout, loading }) => {
  if (loading) {
    return (
      <div
        className="loading-indicator"
        style={{ display: "block", textAlign: "center", marginTop: "30px" }}
      >
        Loading...
      </div>
    );
  }
  const checkAdminForHeader = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Header handleLogout={handleLogout} />
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
    return <></>;
  };

  return (
    <>
      <Route render={({ location }) => checkAdminForHeader(location)} />
      <div className="contentWrapper">
        <Switch>
          <Route exact path="/">
            <Home cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route path="/admin/cocktails">
            <CocktailAdmin />
          </Route>
          <Route path="/admin/tags">
            <TagAdmin />
          </Route>
          <Route path="/admin/terminologies">
            <TerminologyAdmin />
          </Route>
          <Route path="/cocktails/search">
            <CocktailSearch />
          </Route>
          <Route path="/bars" component={Bar} />
          <Route path="/cocktails/:id" component={CocktailDetailSearch} />
          <Route path="/recommend">
            <Question cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route path="/result">
            <Result cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route exact path="/mypage">
            <MyPage />
          </Route>
          <Route path="/mypage/profile">
            <MyProfile />
          </Route>
          <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} />
          <Route path="/login">
            <Login />
          </Route>
          <Route path="/signup">
            <Signup />
          </Route>
        </Switch>
      </div>
      <Route render={({ location }) => checkAdminForFooter(location)} />
    </>
  );
};

export default Routes;
