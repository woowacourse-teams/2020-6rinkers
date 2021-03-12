import React, { useState } from "react";
import { createResourceByCsv } from "../../../api";
import FileUploadButton from "../../mypage/FileUploadButton";

const FileUploadContainer = () => {
  const TAG_RESOURCE_NAME = "tags";
  const COCKTAIL_RESOURCE_NAME = "cocktails";
  const TERMINOLOGY_RESOURCE_NAME = "terminologies";
  const INGREDIENT_RESOURCE_NAME = "ingredients";

  const [file, setFile] = useState({});
  const handleFile = (e) => {
    setFile({ file: e.target.files[0] });
  };

  const resetFile = () => {
    setFile({});
  };

  const uploadResourceFile = (e, resourceName) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file.file);

    createResourceByCsv(resourceName, formData);

    resetFile();
  };

  return (
    <div className="file-upload">
      <input type="file" name="file" onChange={(e) => handleFile(e)} />
      <FileUploadButton
        resourceName={TAG_RESOURCE_NAME}
        uploadResourceFile={uploadResourceFile}
      />
      <FileUploadButton
        resourceName={COCKTAIL_RESOURCE_NAME}
        uploadResourceFile={uploadResourceFile}
      />
      <FileUploadButton
        resourceName={TERMINOLOGY_RESOURCE_NAME}
        uploadResourceFile={uploadResourceFile}
      />
      <FileUploadButton
        resourceName={INGREDIENT_RESOURCE_NAME}
        uploadResourceFile={uploadResourceFile}
      />
    </div>
  );
};

export default FileUploadContainer;
