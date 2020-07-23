import React, { useState } from "react";
import InputContainer from "./InputContainer";
import "../../css/editFormContainer.css";
import {createCocktail} from "../../api";
import dataToCocktailRequest from "../../utils/cocktailConverter";

const EditFormContainer = ({ cocktail, updateCocktail, onResetCocktail }) => {
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
      <div className="editForms">
        {Object.keys(cocktail).map((property, index) => (
          <InputContainer
            property={property}
            value={cocktail[property]}
            onChange={onChange}
            onEnter={onEnter}
            key={"property" + index}
          />
        ))}
      </div>
      <div className="submit">
        <button onClick={onSubmit}>저장/수정</button>
      </div>
    </>
  );
};

export default EditFormContainer;
