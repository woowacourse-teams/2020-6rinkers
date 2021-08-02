import React from "react";

const UserRecipeItem = ({ userCocktail, removeRecipe }) => {
  const strGA = 44032; //가
  const strHI = 55203; //힣

  const reulReturner = (label) => {
    const lastStrCode = label.charCodeAt(label.length - 1);
    let prop = true;
    let msg;

    if (lastStrCode < strGA || lastStrCode > strHI) {
      return false; //한글이 아님
    }

    if ((lastStrCode - strGA) % 28 === 0) prop = false;

    if (prop) {
      msg = label + "을 ";
    } else {
      msg = label + "를 ";
    }

    return msg;
  };

  const urouReturner = (label) => {
    const lastStrCode = label.charCodeAt(label.length - 1);
    let prop = true;
    let msg;

    if (lastStrCode < strGA || lastStrCode > strHI) {
      return false; //한글이 아님
    }

    if ((lastStrCode - strGA) % 28 === 0) prop = false;

    if (prop) {
      msg = label + "으로 ";
    } else {
      msg = label + "로 ";
    }

    return msg;
  };

  return (
    <div className="user-recipe-item-container">
      {userCocktail
        ? userCocktail.userRecipeItemRequests.map((it, index) => (
            <div className="user-recipe-item" key={"recipe" + index}>
              {reulReturner(it.ingredientName)}{" "}
              {urouReturner(it.quantityUnitName)}
              {it.quantity}{" "}
              {it.quantityUnitId === 5 || it.quantityUnitId === 6
                ? " 개"
                : " 잔"}
              <div className="a" onClick={removeRecipe} data-id={index}>
                <img
                  className="trash-bin-image"
                  src="/image/trash-bin.png"
                  alt="trash-bin"
                />
              </div>
            </div>
          ))
        : ""}
    </div>
  );
};

export default UserRecipeItem;
