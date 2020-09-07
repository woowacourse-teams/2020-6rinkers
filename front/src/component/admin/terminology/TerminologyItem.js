import React from "react";
import { terminologyAdminState } from "../../../recoil";
import { useSetRecoilState } from "recoil/dist";
import { deleteTerminology, fetchTerminology } from "../../../api";
import { TERMINOLOGY_ADMIN_PROTOTYPE } from "../../../constants";

const TerminologyItem = ({ terminology }) => {
  const setTerminologyAdmin = useSetRecoilState(terminologyAdminState);

  const onUpdateTerminologyAdmin = async (e) => {
    e.preventDefault();
    const response = await fetchTerminology(terminology.id);
    setTerminologyAdmin(response.data);
  };

  const onDeleteTerminologyAdmin = async (e) => {
    e.stopPropagation();
    await deleteTerminology(terminology.id);
    setTerminologyAdmin(TERMINOLOGY_ADMIN_PROTOTYPE);
  };

  return (
    <div className="terminology-item" onClick={onUpdateTerminologyAdmin}>
      <img src={terminology.imageUrl} alt={terminology.name} />
      <div className="terminology-text-information">
          <div>name: {terminology.name}</div>
          <div>type: {terminology.terminologyType}</div>
          <div>description: {terminology.description}</div>
      </div>
      <button onClick={onDeleteTerminologyAdmin}>삭제</button>
    </div>
  );
};

export default TerminologyItem;
