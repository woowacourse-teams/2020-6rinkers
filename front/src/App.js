import React from "react";
import "./index.css";
import EditFormContainer from "./component/EditFormContainer";
import CocktailListContainer from "./component/CocktailListContainer";
import Header from "./component/Header";

function App() {
  return (
    <div className="App">
      <Header />
      <div className="main">
        <div className="editFormContainer">
          <EditFormContainer />
        </div>
        <div className="cocktailListContainer">
          <CocktailListContainer />
        </div>
      </div>
    </div>
  );
}

export default App;
