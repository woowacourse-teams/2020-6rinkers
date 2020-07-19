import React, { useState } from "react";
import EditFormContainer from "./EditFormContainer";
import CocktailListContainer from "./CocktailListContainer";

const Main = () => {
  const [cocktail, setCocktail] = useState({});

  const onUpdateCocktail = (data) => {
    setCocktail(data);
  };

  return (
    <div className="main">
      <div className="editFormContainer" updateCocktail={onUpdateCocktail}>
        <EditFormContainer />
      </div>
      <div className="cocktailListContainer">
        <CocktailListContainer />
      </div>
    </div>
  );
};

export default Main;
