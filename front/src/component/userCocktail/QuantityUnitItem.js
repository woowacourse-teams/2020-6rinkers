import React from "react";

const QuantityUnitItem = ({ unitItem }) => {
  return (
    <div className="quantity-unit-item-container">
      <img
        className="quantity-unit-item-image"
        src={unitItem.path}
        alt={unitItem.name}
      />
      <div className="quantity-unit-item">{unitItem.name}</div>
    </div>
  );
};

export default QuantityUnitItem;
