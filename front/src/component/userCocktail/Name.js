import React from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import "../../css/userCocktail/name.css";
import { Redirect, useHistory, useLocation } from "react-router-dom";
import { userCocktailState, userState } from "../../recoil";
import Alert from "react-s-alert";
import { loadConfig } from "../alert/Alerts";

const Name = ({ setStage }) => {
  const history = useHistory();
  const location = useLocation();
  const isLogin = useRecoilValue(userState).currentUser.id !== 0;
  const [userCocktail, setUserCocktail] = useRecoilState(userCocktailState);
  let name = "";

  if (!isLogin) {
    Alert.info(
      "<p>나만의 칵테일 만들기 위해</p><p>로그인해주세요.</p>",
      loadConfig()
    );
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  }

  const onNameChange = (e) => {
    name = e.target.value;
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      onNext(e);
    }
  };

  const onNext = (e) => {
    e.preventDefault();

    if (name === "") {
      alert("이름을 입력해주세요!");
      return;
    }

    setUserCocktail({
      ...userCocktail,
      name: name,
    });
    history.push("/my-cocktail/ingredients");
    setStage("ingredients");
  };

  return (
    <div className="name-container">
      <input
        className="name-input"
        onChange={onNameChange}
        onKeyPress={handleKeyPress}
        type="text"
        autoFocus={true}
      />
      <button className="next-stage" type="submit" onClick={onNext}>
        <p className="content">칵테일 재료를 정해보아요</p>
      </button>
    </div>
  );
};

export default Name;
