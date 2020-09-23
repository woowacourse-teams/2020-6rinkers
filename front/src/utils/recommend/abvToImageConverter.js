import React from "react";

export const abvToImageConverter = (value) => {
  if (value === 0) {
    return "🙅🏻‍";
  }
  if (value < 5) {
    return "👶";
  }
  if (value >= 5 && value < 8) {
    return "🍺";
  }
  if (value >= 8 && value < 13) {
    return <img className="rice-wine" src="/image/rice_wine.png" alt="막걸리" />;
  }
  if (value >= 13 && value < 18) {
    return "🍷";
  }
  if (value >= 18 && value < 25) {
    return <img className="soju" src="/image/soju.png" alt="소주" />;
  }
  if (value >= 25 && value < 33) {
    return "🍸";
  }
  if (value >= 33 && value < 43) {
    return <img className="whisky" src="/image/whisky.png" alt="위스키" />;
  }
  if (value >= 43 && value < 53) {
    return "💣";
  }
  if (value >= 53) {
    return "💀";
  }
  return value;
};
