import React from "react";

const GlassItem = ({ glass }) => {
  return (
    <div className="glass-item-container">
      <div className="glass-item" data-id={glass.id}>
        {glass.name}
      </div>
    </div>
  );
};

export default GlassItem;
