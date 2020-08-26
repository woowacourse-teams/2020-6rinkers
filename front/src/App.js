import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import "./index.css";
import Alert from "react-s-alert";
import Routes from "./router/Routes";
import { getCurrentUser } from "./api";
import { ACCESS_TOKEN, USER_PROTOTYPE } from "./constants";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import { userState } from "./recoil";
import { ACCESS_TOKEN, USER_PROTOTYPE } from "./constants";
import { getCurrentUser } from "./utils/APIUtils";

const App = () => {
  const [cocktails, setCocktails] = useState([]);
  const [user, setUser] = useRecoilState(userState);
  const [loading, setLoading] = useState(false);

  const { authenticated, currentUser } = user;

  const loadCurrentlyLoggedInUser = () => {
    setLoading(true);

    getCurrentUser()
      .then((response) => {
        setUser({
          currentUser: response.data,
          authenticated: true,
        });
        setLoading(false);
      })
      .catch((error) => {
        setLoading(false);
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

  useEffect(() => {
    if (localStorage.getItem(ACCESS_TOKEN)) {
      loadCurrentlyLoggedInUser();
    }
  }, []);

  return (
    <div className="App">
      <Routes
        cocktails={cocktails}
        setCocktails={setCocktails}
        authenticated={authenticated}
        currentUser={currentUser}
        handleLogout={handleLogout}
        loading={loading}
      />
      <Alert
        stack={{ limit: 3 }}
        timeout={3000}
        position="top-right"
        effect="slide"
        offset={65}
      />
    </div>
  );
};

export default App;
