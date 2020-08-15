import React from 'react';

function NextStage({addAnswer}) {
    return (
        <button className="next-stage" onClick={addAnswer}>완료</button>
    );
}

export default NextStage;