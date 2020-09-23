import React, { useState } from "react";
import { Range } from "rc-slider/es";
import { abvToImageConverter } from "../../utils/recommend/abvToImageConverter";
import "rc-slider/assets/index.css";
import "../../css/recommend/abv.css";
import NextStage from "./NextStage";

const Abv = ({ addAnswer }) => {
  const [abvs, setAbvs] = useState([15, 40]);

  const changeValues = (data) => {
    setAbvs(data);
  };

  const abvToAnswer = () => {
    return {
      max: abvs[1],
      min: abvs[0],
    };
  };

  return (
    <div className="abv-container">
      <div className="abv">
        <div className="min-abv">
          <div className="abv-value">{abvs[0]}</div>
          {abvToImageConverter(abvs[0])}
        </div>
        <div className="max-abv">
          <div className="abv-value">{abvs[1]}</div>
          {abvToImageConverter(abvs[1])}
        </div>
      </div>
      <div className="slider-area">
        <Range
          marks={{
            0: "무알콜",
            55: "55",
          }}
          min={0}
          max={55}
          defaultValue={[15, 40]}
          allowCross={false}
          onChange={changeValues}
        />
      </div>
      <NextStage
        type="abvAnswer"
        answer={abvToAnswer()}
        addAnswer={addAnswer}
        done
        saying="이정도 도수로 부탁해요"
      />
    </div>
  );
};

export default Abv;
