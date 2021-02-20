import React from "react";
import { ProgressBar, Step } from "react-step-progress-bar";

const StepProgressBar = ({ percent }) => {
  const count = 5;

  const steps = [];

  for (let i = 0; i < count; i++) {
    steps.push(
      <Step>
        {({ accomplished }) => (
          <div className={`indexed-step ${accomplished ? "accomplished" : ""}`}>
            {i + 1}
          </div>
        )}
      </Step>
    );
  }

  return (
    <ProgressBar className="step-progress-bar" percent={percent}>
      {steps}
    </ProgressBar>
  );
};

export default StepProgressBar;
