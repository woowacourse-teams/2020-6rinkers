import React, { useEffect, useState } from "react";
import { fetchUserCocktail } from "../../api";
import Alert from "react-s-alert";
import { useHistory } from "react-router-dom";

const UserCocktailDetail = (props) => {
  const { id } = props.match.params;
  const [userCocktail, setUserCocktail] = useState();
  const history = useHistory();

  const onLoadUserCocktail = async (id) => {
    try {
      const response = await fetchUserCocktail(id);
      const { data } = response;
      setUserCocktail(data);
    } catch (e) {
      Alert.error("나만의 칵테일 조회에 실패했습니다.");
    }
  };

  useEffect(() => onLoadUserCocktail(id), []);

  const onRedirect = () => {
    history.push("/my-cocktail");
  };

  console.log(userCocktail);
  return (
    <div className="user-cocktail-detail-container">
      {/* 칵테일 이름, 설명, 제작자, 레시피 ->레시피에서 도수, 양, 색을 뽑아낼 수 있다. 뒤로가기*/}
      <div>{userCocktail && userCocktail.name}</div>
      <div>{userCocktail && userCocktail.description}</div>
      <div>{userCocktail && userCocktail.authorName}</div>
      <button onClick={onRedirect}>목록으로</button>
    </div>
  );
};

export default UserCocktailDetail;
