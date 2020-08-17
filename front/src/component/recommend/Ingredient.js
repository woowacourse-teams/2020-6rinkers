import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";

const Ingredient = ({ addAnswer }) => {
  const [ingredients, setIngredients] = useState([]);
  const [answer, setAnswer] = useState({});

  useEffect(() => {
    // api
    const updateIngredients = async () => {
      await setIngredients(["라임", "레몬", "맥주"]);
    };
    updateIngredients();
  }, []);

  useEffect(() => {
    if (ingredients.length === 0) {
      return;
    }
    const updateAnswer = async () => {
      await setAnswer(
        ingredients.reduce((o, key) => ({ ...o, [key]: "SOSO" }), {})
      );
    };
    updateAnswer();
  }, [ingredients]);

  const onChangeAnswer = (name, userAnswer) => {
    setAnswer({
      ...answer,
      [name]: userAnswer,
    });
  };

  return (
    <div>
      {ingredients &&
        ingredients.map((ingredient, index) => {
          return (
            <Tag
              name={ingredient}
              key={`key-${index}`}
              answer={answer}
              onChangeAnswer={onChangeAnswer}
            />
          );
        })}
      <NextStage
        type="preferenceAnswers"
        answer={answer}
        addAnswer={addAnswer}
        saying="다음 질문으로"
      />
    </div>
  );
};

export default Ingredient;
