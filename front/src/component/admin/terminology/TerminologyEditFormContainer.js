import React from "react";

const TerminologyEditFormContainer = ({ terminologyAdmin, onChange }) => {
  return (
    <div className="terminologies-edit-form-container">
      {Object.keys(terminologyAdmin).map((key, index) => (
        <div className="form-row" key={`terminology-input-${index}`}>
          <div>{key}</div>
          <input
            type="text"
            name={key}
            value={terminologyAdmin[key]}
            onChange={onChange}
          />
        </div>
      ))}
      <button>저장/수정하기</button>
    </div>
  );
};

export default TerminologyEditFormContainer;
