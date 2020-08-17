import React, { useState } from "react";
import { abvToImageConverter } from "../../utils/recommend/abvToImageConverter";
import { Range } from "rc-slider/es";
import "rc-slider/assets/index.css";
import "../../css/recommend/abv.css";
import NextStage from "./NextStage";

const Abv = ({ addAnswer }) => {
  const [abvs, setAbvs] = useState([0, 80]);

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
    <div>
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
      <div className="sliderArea">
        <Range
          marks={{
            0: "무알콜",
            80: "80",
          }}
          min={0}
          max={80}
          defaultValue={[0, 80]}
          allowCross={false}
          onChange={changeValues}
        />
      </div>
      <NextStage
        type="abvAnswer"
        answer={abvToAnswer()}
        addAnswer={addAnswer}
        saying="이정도 도수로 부탁해요"
      />
    </div>
  );
};

export default Abv;
