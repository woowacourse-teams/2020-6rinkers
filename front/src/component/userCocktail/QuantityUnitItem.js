import React from "react";

const QuantityUnitItem = ({ unitItem, onSelect }) => {
  return (
    <div
      className="quantity-unit-item-container"
      onClick={onSelect}
      data-id={unitItem.id}
    >
      <div className="quantity-unit-item">
        {unitItem.name}
        <img
          className="quantity-unit-item-image"
          src={unitItem.path}
          alt={unitItem.name}
        />
      </div>
    </div>
  );
};

export default QuantityUnitItem;
