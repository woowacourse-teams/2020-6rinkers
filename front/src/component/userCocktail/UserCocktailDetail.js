import React, { useEffect, useState } from "react";
import { fetchUserCocktail } from "../../api";
import Alert from "react-s-alert";

const UserCocktailDetail = (props) => {
  const { id } = props.match.params;
  const [userCocktail, setUserCocktail] = useState();

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

  console.log(userCocktail);
  return (
    <div>
      <div>{userCocktail && userCocktail.name}</div>
    </div>
  );
};

export default UserCocktailDetail;
