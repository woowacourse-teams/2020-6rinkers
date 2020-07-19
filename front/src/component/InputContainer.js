import React from "react";

function InputContainer({ property, value, onChange }) {
  return (
    <div className="inputContainer">
      <div>{property}</div>
      <input
        className="inputBox"
        name={property}
        onChange={onChange}
        value={value}
      />
    </div>
  );
}

export default InputContainer;
