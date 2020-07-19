import React, { useState } from "react";
import InputContainer from "./InputContainer";
import "../css/editFormContainer.css";

const EditFormContainer = ({ updateCocktail }) => {
  const [cocktailData, setCocktailData] = useState({
    name: "",
    abv: "",
    description: "",
    origin: "",
    imageUrl: "",
    tag: {},
    sweet: "",
    sour: "",
    bitter: "",
    liquor: {},
    liquorQuantity: {},
    special: {},
    specialQuantity: {},
  });

  const onChange = (e) => {
    e.preventDefault();
    const { value, name } = e.target;
    setCocktailData({
      ...cocktailData,
      [name]: value,
    });
    console.log(cocktailData);
  };

  return (
    <>
      <div className="editForms">
        {Object.keys(cocktailData).map((property, index) => (
          <InputContainer
            property={property}
            onChange={onChange}
            key={"property" + index}
          />
        ))}
      </div>
      <div className="submit">
        <button>저장/수정</button>
      </div>
    </>
  );
};

export default EditFormContainer;
