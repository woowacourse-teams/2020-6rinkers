import React from "react";

const GlassItem = ({ glass, onSelect }) => {
  return (
    <div className="glass-item-container" onClick={onSelect}>
      <div className="glass-item" data-id={glass.id}>
        {glass.name}
      </div>
    </div>
  );
};

export default GlassItem;
