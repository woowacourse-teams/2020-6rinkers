import React from "react";
import { Route } from "react-router-dom";
import Construction from "../component/construction/Construction";
import Header from "../component/common/Header";
import Footer from "../component/common/Footer";

const Routes = () => {
  return (
    <>
      <Header />
      <div className="contentWrapper">
        <Route path="/" component={Construction} />
      </div>
      <Footer />
    </>
  );
};

export default Routes;
