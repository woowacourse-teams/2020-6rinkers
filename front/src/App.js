import React, { useState } from "react";
import "./index.css";
import Routes from "./router/Routes";

const App = () => {
  const [cocktails, setCocktails] = useState([]);
  return (
    <div className="App">
      <Routes cocktails={cocktails} setCocktails={setCocktails} />
    </div>
  );
};

export default App;
