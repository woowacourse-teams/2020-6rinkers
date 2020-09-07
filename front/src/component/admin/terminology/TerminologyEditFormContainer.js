import React from "react";
import { useRecoilValue } from "recoil";
import { terminologyAdminState } from "../../../recoil";

const TerminologyEditFormContainer = ({ onChange }) => {
  const terminologyAdmin = useRecoilValue(terminologyAdminState);

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
