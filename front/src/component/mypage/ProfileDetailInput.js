import React from "react";

export default ({ name, value, onChange }) => {
  return (
    <div className="profile-input-container">
      <h3>{name}</h3>
      <div>
        <input type="text" name={name} value={value} onChange={onChange()} />
      </div>
    </div>
  );
};
