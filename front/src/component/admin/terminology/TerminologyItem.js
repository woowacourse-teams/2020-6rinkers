import React from "react";

const TerminologyItem = ({ terminology }) => {
  return (
    <div className="terminology-item">
      <div>{terminology.name}</div>
      <div>{terminology.terminologyType}</div>
      <div>{terminology.description}</div>
    </div>
  );
};

export default TerminologyItem;
