import React, { useEffect, useState } from "react";
import Tag from "./Tag";
import NextStage from "./NextStage";
import { fetchThreeRandomIngredientTags } from "../../api";

const Ingredient = ({ addAnswer }) => {
  const [ingredients, setIngredients] = useState([]);
  const [answer, setAnswer] = useState([]);

  const updateIngredients = async () => {
    const response = await fetchThreeRandomIngredientTags();
    const data = response["data"];
    await setIngredients(data);
  };

  useEffect(() => {
    updateIngredients();
  }, []);

  const updateAnswer = async () => {
    if (ingredients.length === 0) {
      return;
    }
    await setAnswer(
      answer.concat(
        ingredients.map((ingredient) => ({
          tagId: ingredient.tagId,
          userPreferenceAnswer: "",
        }))
      )
    );
  };

  useEffect(() => {
    updateAnswer();
  }, [ingredients]);

  const onChangeAnswer = (tagId, userAnswer) => {
    const clonedAnswer = [...answer];
    const targetAnswer = clonedAnswer.find((each) => each.tagId === tagId);
    targetAnswer.userPreferenceAnswer = userAnswer;
    setAnswer(clonedAnswer);
  };

  const checkClicked = (tagId) => {
    const targetAnswer = answer.find((each) => each.tagId === tagId);
    return targetAnswer ? targetAnswer.userPreferenceAnswer : "";
  };

  const checkDone = () => {
    return answer.filter((ans) => ans.userPreferenceAnswer === "").length === 0;
  };

  return (
    <div>
      {ingredients &&
        ingredients.map((ingredient, index) => {
          return (
            <Tag
              name={ingredient.name}
              tagId={ingredient.tagId}
              key={`key-${index}`}
              answer={checkClicked(ingredient.tagId)}
              onChangeAnswer={onChangeAnswer}
            />
          );
        })}
      <NextStage
        type="preferenceAnswers"
        answer={answer}
        addAnswer={addAnswer}
        done={checkDone()}
        saying="다음 질문으로"
      />
    </div>
  );
};

export default Ingredient;
