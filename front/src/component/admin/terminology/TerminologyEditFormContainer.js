import React from "react";
import { useRecoilValue } from "recoil";
import { terminologyAdminState } from "../../../recoil";
import { createTerminology, updateTerminology } from "../../../api";

const TerminologyEditFormContainer = ({ onChange }) => {
  const terminologyAdmin = useRecoilValue(terminologyAdminState);

  const saveOrUpdateTerminologyAdmin = async () => {
    if (terminologyAdmin.id === 0) {
      await createTerminology(terminologyAdmin);
    } else {
      await updateTerminology(terminologyAdmin.id, terminologyAdmin);
    }
  };

  return (
    <div className="terminologies-edit-form-container">
      {Object.keys(terminologyAdmin).map((key, index) => (
        <div className="form-row" key={`terminology-input-${index}`}>
          <div className="terminology-key">{key}</div>
          <input
            className="terminology-input"
            type="text"
            name={key}
            value={terminologyAdmin[key]}
            onChange={onChange}
          />
        </div>
      ))}
      <button className="submit-btn" onClick={saveOrUpdateTerminologyAdmin}>저장/수정하기</button>
    </div>
  );
};

export default TerminologyEditFormContainer;
