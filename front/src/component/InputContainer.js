import React from "react";

function InputContainer({ property, onChange }) {
  return (
    <div className="inputContainer">
      <div>{property}</div>
      <input className="inputBox" name={property} onChange={onChange} />
    </div>
  );
}

export default InputContainer;
