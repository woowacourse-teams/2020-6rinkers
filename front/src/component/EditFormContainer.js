import React, { useState } from "react";
import InputContainer from "./InputContainer";
import "../css/editFormContainer.css";

const EditFormContainer = ({ cocktail, updateCocktail, onResetCocktail }) => {
  const onChange = (e) => {
    const { value, name } = e.target;
    console.log(value, name);
    updateCocktail(value, name);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    // api 쏘기
    onResetCocktail();
  };

  return (
    <>
      <div className="editForms">
        {Object.keys(cocktail).map((property, index) => (
          <InputContainer
            property={property}
            value={cocktail[property]}
            onChange={onChange}
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
