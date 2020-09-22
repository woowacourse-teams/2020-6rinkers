import React from "react";

const CocktailInputContainer = ({ property, value, onChange, onEnter }) => {
  return (
    <div className="input-container">
      <div>{property}</div>
      <input
        className="input-box"
        name={property}
        onChange={onChange}
        value={value}
        onKeyDown={onEnter}
      />
    </div>
  );
};

export default CocktailInputContainer;
