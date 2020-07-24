import React from 'react';

const TodayPickContent = ({ name, tags, detail }) => {
  const tagNames = tags.map((tag) => "#".concat(tag)).join(" ");

  return (
    <div className="today-pick-content">
      <div className="cocktail">
        <p className="name">{name}</p>
        <p className="tag">{tagNames}</p>
      </div>
      <div className="detail">{detail}</div>
    </div>
  );
}

export default TodayPickContent;
