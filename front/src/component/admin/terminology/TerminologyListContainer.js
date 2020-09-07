import React from "react";
import TerminologyItem from "./TerminologyItem";

const TerminologyListContainer = ({ terminologiesAdmin }) => {
  return (
    <div className="terminology-list-container">
      {terminologiesAdmin &&
        terminologiesAdmin.map((terminology, index) => (
          <TerminologyItem
            key={`terminology-${index}`}
            terminology={terminology}
          />
        ))}
    </div>
  );
};

export default TerminologyListContainer;
