import React, { useEffect } from "react";
import { fetchAllTerminologies } from "../../../api";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  terminologiesAdminState,
  terminologyAdminState,
  userState,
} from "../../../recoil";
import { Redirect, useLocation } from "react-router-dom";
import "../../../css/admin/terminologyAdmin.css";
import TerminologyEditFormContainer from "./TerminologyEditFormContainer";
import TerminologyListContainer from "./TerminologyListContainer";

const TerminologyAdmin = (props) => {
  const role = useRecoilValue(userState).currentUser.role;
  const [terminologyAdmin, setTerminologyAdmin] = useRecoilState(
    terminologyAdminState
  );
  const [terminologiesAdmin, setTerminologiesAdmin] = useRecoilState(
    terminologiesAdminState
  );
  const location = useLocation();

  const fetchTerminologies = async () => {
    const response = await fetchAllTerminologies();
    setTerminologiesAdmin(response.data);
  };

  useEffect(() => {
    fetchTerminologies();
  }, [terminologyAdmin]);

  if (role !== "ROLE_ADMIN") {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  }

  const onChange = (e) => {
    const { value, name } = e.target;
    setTerminologyAdmin({ ...terminologyAdmin, [name]: value });
  };

  return (
    <div className="terminology-admin">
      <TerminologyEditFormContainer onChange={onChange} />
      <TerminologyListContainer />
    </div>
  );
};

export default TerminologyAdmin;
