import React from "react";
import CocktailInputContainer from "./CocktailInputContainer";
import "../../../css/admin/editFormContainer.css";
import { createCocktail } from "../../../api";
import dataToCocktailRequest from "../../../utils/admin/cocktailConverter";
import FileUploadContainer from "../common/FileUplodeContainer";

const CocktailEditFormContainer = ({
  cocktail,
  updateCocktail,
  onResetCocktail,
}) => {
  const onChange = (e) => {
    const { value, name } = e.target;
    console.log(value, name);
    updateCocktail(value, name);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const result = createCocktail(dataToCocktailRequest(cocktail));
    console.log(result);
    onResetCocktail();
  };

  const onEnter = (e) => {
    if (e.key === "Enter") {
      onSubmit(e);
    }
  };

  return (
    <>
      <div className="fileUploadContainer">
        <FileUploadContainer />
      </div>
      <div className="editForms">
        {Object.keys(cocktail).map((property, index) => (
          <CocktailInputContainer
            property={property}
            value={cocktail[property]}
            onChange={onChange}
            onEnter={onEnter}
            key={"property" + index}
          />
        ))}
      </div>
      <div className="submit">
        <button type="submit" onClick={onSubmit}>
          저장/수정
        </button>
      </div>
    </>
  );
};

export default CocktailEditFormContainer;