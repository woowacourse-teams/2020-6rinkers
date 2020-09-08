import React from "react";

export default ({ name, userKey, value, onChange, disabled }) => {
  return (
    <div className="profile-input-container">
      <h3>{name}</h3>
      <div className="detail-input">
        <input
          type="text"
          name={userKey}
          value={value}
          onChange={onChange}
          disabled={disabled}
        />
      </div>
    </div>
  );
};
