import React from "react";
import CocktailInputContainer from "./CocktailInputContainer";
import "../../../css/admin/editFormContainer.css";
import {
  createCocktail,
  deleteAllCocktail,
  updateCocktail,
} from "../../../api";
import { convertDataToCocktailRequest } from "../../../utils/admin/cocktailConverter";
import FileUploadContainer from "../common/FileUplodeContainer";

const CocktailEditFormContainer = ({
  cocktail,
  onUpdateCocktail,
  onResetCocktail,
}) => {
  const onChange = (e) => {
    const { value, name } = e.target;
    onUpdateCocktail(value, name);
  };

  const onSubmit = async (e) => {
    e.preventDefault();

    cocktail.id === "0"
      ? await createCocktail(convertDataToCocktailRequest(cocktail))
      : await updateCocktail(
          cocktail.id,
          convertDataToCocktailRequest(cocktail)
        );

    onResetCocktail();
  };

  const onEnter = (e) => {
    if (e.key === "Enter") {
      onSubmit(e);
    }
  };

  const deleteAllCocktails = () => {
    if (window.confirm("정말로 실행하시겠습니까?")) deleteAllCocktail();
  };

  return (
    <>
      <div className="file-upload-container">
        <FileUploadContainer />
      </div>
      <button
        className="cocktails-delete-btn"
        type="button"
        onClick={deleteAllCocktails}
      >
        모든 칵테일 삭제하기
      </button>
      <div className="edit-forms">
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
