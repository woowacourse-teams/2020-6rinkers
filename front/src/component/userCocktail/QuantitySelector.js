import React, { useEffect, useState } from "react";
import div from "infinite-react-carousel";

const QuantitySelector = ({ updateQuantity, imagePath }) => {
  const [value, setValue] = useState(1.0);

  const updateValueByClick = (e, updating) => {
    e.preventDefault();
    const updated = value + parseFloat(updating);
    setValue(parseFloat(updated.toFixed(1)));
  };

  const updateValueByTyping = (e) => {
    e.preventDefault();
    const updated = parseFloat(e.target.value);
    setValue(parseFloat(updated.toFixed(1)));
  };

  useEffect(() => {
    updateQuantity(value ? value : 0.0);
  }, [value]);

  return (
    <div className="quantity-selector-container">
      <div className="minus-selector-container">
        <div
          className="select-button minus-zero-point-one"
          onClick={(e) => updateValueByClick(e, -0.1)}
        >
          -0.1
        </div>
        <div
          className="select-button minus-one-point-zero"
          onClick={(e) => updateValueByClick(e, -1.0)}
        >
          -1.0
        </div>
        <div
          className="select-button minus-zero-point-five"
          onClick={(e) => updateValueByClick(e, -0.5)}
        >
          -0.5
        </div>
      </div>
      <div className="image-and-input-container">
        <img
          className="quantity-unit-image"
          src={imagePath}
          alt="quantity-unit-image"
        />
        <input
          className="quantity-input"
          type="number"
          value={value}
          onChange={updateValueByTyping}
        />
      </div>
      <div className="plus-selector-container">
        <div
          className="select-button plus-zero-point-one"
          onClick={(e) => updateValueByClick(e, 0.1)}
        >
          +0.1
        </div>
        <div
          className="select-button plus-one-point-zero"
          onClick={(e) => updateValueByClick(e, 1.0)}
        >
          +1.0
        </div>
        <div
          className="select-button plus-zero-point-five"
          onClick={(e) => updateValueByClick(e, 0.5)}
        >
          +0.5
        </div>
      </div>
    </div>
  );
};

export default QuantitySelector;
