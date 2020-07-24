import React from "react";
import Navbar from "../component/Navbar";
import "../css/Main.css";
import RandomCocktail from "../component/RandomCocktail";
import Footer from "../component/Footer";

const Main = () => {
  return (
    <div className="main-page">
      <Navbar />
      <hr className="solid" />
      <RandomCocktail />
      <Footer />
    </div>
  );
};

export default Main;
