import React from "react";
import { Redirect, useLocation } from "react-router-dom";
import { ACCESS_TOKEN } from "../constants";

const OAuth2RedirectHandler = () => {
  const location = useLocation();

  const getUrlParameter = (name) => {
    const setName = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[\\?&]" + setName + "=([^&#]*)");

    const results = regex.exec(location.search);
    return results === null
      ? ""
      : decodeURIComponent(results[1].replace(/\+/g, " "));
  };

  const token = getUrlParameter("token");
  const error = getUrlParameter("error");

  if (token) {
    localStorage.setItem(ACCESS_TOKEN, token);
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location },
        }}
      />
    );
  }
  return (
    <Redirect
      to={{
        pathname: "/login",
        state: {
          from: location,
          error,
        },
      }}
    />
  );
};

export default OAuth2RedirectHandler;
