import React from "react";
import { terminologyAdminState } from "../../../recoil";
import { useSetRecoilState } from "recoil/dist";
import { fetchTerminology } from "../../../api";

const TerminologyItem = ({ terminology }) => {
  const setTerminologyAdmin = useSetRecoilState(terminologyAdminState);

  const onUpdateTerminologyAdmin = async (e) => {
    e.preventDefault();
    console.log(terminology.id);
    const response = await fetchTerminology(terminology.id);
    console.log(response.data);
    setTerminologyAdmin(response.data);
  };

  return (
    <div className="terminology-item" onClick={onUpdateTerminologyAdmin}>
      <div>{terminology.name}</div>
      <div>{terminology.terminologyType}</div>
      <div>{terminology.description}</div>
      <button data-terminology-id={terminology.id}>삭제</button>
    </div>
  );
};

export default TerminologyItem;
