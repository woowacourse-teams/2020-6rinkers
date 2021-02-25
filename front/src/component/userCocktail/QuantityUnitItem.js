import React from "react";

const QuantityUnitItem = ({ unitItem, onSelect }) => {
  return (
    <div className="quantity-unit-item-container" onClick={onSelect}>
      <div className="quantity-unit-item" data-id={unitItem.id}>
        {unitItem.name}
      </div>
    </div>
  );
};

export default QuantityUnitItem;
