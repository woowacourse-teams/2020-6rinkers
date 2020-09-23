import React from 'react';

const FileUploadButton = ({resourceName, uploadResourceFile}) => {
    return (
        <button
            className="fileUploadButton"
            type="button"
            onClick={(e) => uploadResourceFile(e, resourceName)}
        >
            {resourceName} CSV 파일 업로드
        </button>
    );
}

export default FileUploadButton;