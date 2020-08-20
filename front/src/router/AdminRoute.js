import React from "react";
import { Route, Redirect } from "react-router-dom";

const AdminRoute = ({ component: Component, currentUser, ...rest }) => (
  <Route
    {...rest}
    render={(props) =>
      currentUser.role === "ROLE_ADMIN" ? (
        <Component {...rest} {...props} />
      ) : (
        <Redirect
          to={{
            pathname: "/login",
            state: { from: props.location },
          }}
        />
      )
    }
  />
);

export default AdminRoute;
