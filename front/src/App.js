import React, { useEffect, useState } from "react";
import "./index.css";
import Alert from "react-s-alert";
import Routes from "./router/Routes";
import { getCurrentUser } from "./utils/APIUtils";
import { ACCESS_TOKEN } from "./constants";

const App = () => {
  const [cocktails, setCocktails] = useState([]);
  const [user, setUser] = useState({
    authenticated: false,
    currentUser: null,
    loading: false,
  });

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
      })
      .catch((error) => {
        setUser({
          loading: false,
        });
      });
  };

  useEffect(loadCurrentlyLoggedInUser, []);

  const handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    setUser({
      authenticated: false,
      currentUser: null,
    });
    Alert.success("You're safely logged out!");
  };

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

  return (
    <div className="App">
      <Routes
        cocktails={cocktails}
        setCocktails={setCocktails}
        authenticated={authenticated}
        currentUser={currentUser}
        loading={loading}
        handleLogout={handleLogout}
      />
    </div>
  );
};

export default App;
