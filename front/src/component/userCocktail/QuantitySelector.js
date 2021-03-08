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
    setValue(e.target.value);
  };

  useEffect(() => {
    updateQuantity(value ? value : 0.0);
  }, [value]);

  return (
    <div>
      <div>
        <div onClick={(e) => updateValueByClick(e, -0.1)}>-0.1</div>
        <div onClick={(e) => updateValueByClick(e, -1.0)}>-1.0</div>
        <div onClick={(e) => updateValueByClick(e, -0.5)}>-0.5</div>
      </div>
      <div>
        <img src={imagePath} alt="quantity-unit-image" />
        <input type="number" value={value} onChange={updateValueByTyping} />
      </div>
      <div>
        <div onClick={(e) => updateValueByClick(e, 0.1)}>+0.1</div>
        <div onClick={(e) => updateValueByClick(e, 1.0)}>+1.0</div>
        <div onClick={(e) => updateValueByClick(e, 0.5)}>+0.5</div>
      </div>
    </div>
  );
};

export default QuantitySelector;
