import React from "react";

const InputContainer = ({ property, value, onChange, onEnter }) => {
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
}

export default InputContainer;
