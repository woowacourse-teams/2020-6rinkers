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
import Result from "../component/recommend/Result";
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
  const checkAdmin = (location) => {
    if (location.pathname.split("/")[1] !== "admin") {
      return (
        <>
          <Header
            authenticated={authenticated}
            currentUser={currentUser}
            handleLogout={handleLogout}
          />
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
        <Route exact path="/">
          <Home cocktails={cocktails} setCocktails={setCocktails} role={role} />
        </Route>
        <Route path="/admin/cocktails">
          <CocktailAdmin role={role} />
        </Route>
        <Route path="/admin/tags">
          <TagAdmin role={role} />
        </Route>
        <Route path="/cocktails/search">
          <CocktailSearch role={role} />
        </Route>
        <Route path="/cocktails/:id" component={CocktailDetailSearch} />
        <Route path="/recommend">
          <Question cocktails={cocktails} setCocktails={setCocktails} />
        </Route>
        <Route path="/result">
          <Result
            cocktails={cocktails}
            setCocktails={setCocktails}
            role={role}
          />
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
    </>
  );
};

export default Routes;
