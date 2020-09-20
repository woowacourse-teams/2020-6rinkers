import React from "react";
import TerminologyItem from "./TerminologyItem";
import { terminologiesAdminState } from "../../../recoil";
import { useRecoilValue } from "recoil/dist";

const TerminologyListContainer = () => {
  const terminologiesAdmin = useRecoilValue(terminologiesAdminState);

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
