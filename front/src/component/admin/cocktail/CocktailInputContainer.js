import React from "react";

const CocktailInputContainer = ({ property, value, onChange, onEnter }) => {
  return (
    <div className="inputContainer">
      <div>{property}</div>
      <input
        className="inputBox"
        name={property}
        onChange={onChange}
        value={value}
        onKeyDown={onEnter}
      />
    </div>
  );
};

export default CocktailInputContainer;
