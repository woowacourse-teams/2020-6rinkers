import React, { useEffect, useState } from "react";
import { Route, Switch } from "react-router-dom";
import Alert from "react-s-alert";
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
// import Result from "../component/recommend/Result";
import OAuth2RedirectHandler from "../oauth2/OAuth2RedirectHandler";
import Login from "../component/user/Login";
import Signup from "../component/user/Signup";
import Profile from "../component/user/Profile";
import { ACCESS_TOKEN, USER_PROTOTYPE } from "../constants";
import { getCurrentUser } from "../utils/APIUtils";

const Routes = ({ cocktails, setCocktails }) => {
  const [user, setUser] = useState({
    authenticated: false,
    currentUser: USER_PROTOTYPE,
    loading: false,
  });
  const [role, setRole] = useState("");

  const { authenticated, currentUser, loading } = user;

  const loadCurrentlyLoggedInUser = () => {
    setUser({
      loading: true,
    });

    getCurrentUser()
      .then((response) => {
        setUser({
          currentUser: response,
          authenticated: true,
          loading: false,
        });
        setRole(response["role"]);
      })
      .catch((error) => {
        setUser({
          loading: false,
        });
      });
  };

  const handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    setUser({
      authenticated: false,
      currentUser: USER_PROTOTYPE,
    });
    Alert.success("로그아웃되었습니다.");
  };

  useEffect(loadCurrentlyLoggedInUser, []);
  console.log(currentUser);

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
          <Header
            authenticated={authenticated}
            currentUser={currentUser}
            handleLogout={handleLogout}
          />
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
          <Route path="/admin/cocktails">
          <CocktailAdmin role={role} />
        </Route>
          <Route path="/admin/tags">
          <TagAdmin role={role} />
        </Route>
          <Route path="/cocktails/search" component={CocktailSearch} />
          <Route path="/bars" component={Bar} />
          <Route path="/cocktails/:id" component={CocktailDetailSearch} />
          <Route path="/recommend">
            <Question cocktails={cocktails} setCocktails={setCocktails} />
          </Route>
          <Route path="/result">
            <Result cocktails={cocktails} />
          </Route>
        <Route path="/oauth2/redirect" component={OAuth2RedirectHandler} />
        <Route path="/login">
          <Login authenticated={authenticated} />
        </Route>
        <Route path="/signup">
          <Signup authenticated={authenticated} />
        </Route>
        <Route path="/profile">
          <Profile role={role} />
        </Route>
      </Switch>
      </div>
      <Route render={({ location }) => checkAdminForFooter(location)} />
    </>
  );
};

export default Routes;
