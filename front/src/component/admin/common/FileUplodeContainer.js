import React, { useState } from "react";
import { createCocktailsByCsv, createTagsByCsv } from "../../../api";

const FileUploadContainer = () => {
  const [file, setFile] = useState({});
  const handleFile = (e) => {
    setFile({ file: e.target.files[0] });
  };

  const resetFile = () => {
    setFile({});
  };

  const onUploadTagFile = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file.file);

    createTagsByCsv(formData);

    resetFile();
  };

  const onUploadCocktailFile = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file.file);

    createCocktailsByCsv(formData);

    resetFile();
  };

  return (
    <div className="fileUpload">
      <input type="file" name="file" onChange={(e) => handleFile(e)} />
      <button className="fileButton" type="button" onClick={onUploadTagFile}>
        태그 CSV 파일 업로드
      </button>
      <button
        className="fileButton"
        type="button"
        onClick={onUploadCocktailFile}
      >
        칵테일 CSV 파일 업로드
      </button>
    </div>
  );
};

export default FileUploadContainer;
