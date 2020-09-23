import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-css-effects/jelly.css";
import { isDesktop } from "../../constants";

const loadConfig = () => {
  return {
    position: isDesktop() ? "top-right" : "bottom",
    timeout: 1800,
    effect: "jelly",
    html: true,
  };
};

export const recommendButtonClickInduceAlert = () => {
  if (localStorage.getItem("recommendButtonClickInduceAlert")) {
    return;
  }
  Alert.info(
    "<p>안녕하세요.</p><p>칵테일 추천 받기 버튼을 눌러 보세요.</p>",
    loadConfig()
  );
  localStorage.setItem("recommendButtonClickInduceAlert", "true");
};

export const favoriteClickInduceAlert = (isAuthenticated) => {
  if (localStorage.getItem("favoriteClickInduceAlert")) {
    return;
  }
  Alert.info(
    isAuthenticated
      ? "<p>별을 눌러 즐겨찾기를 추가해 보세요.</p><p>마이 페이지에서 확인하실 수 있어요.</p>"
      : "<p>로그인하고 즐겨찾기를 이용해 보세요.</p><p>좋아하는 칵테일을 저장할 수 있어요.</p>",
    loadConfig()
  );
  localStorage.setItem("favoriteClickInduceAlert", "true");
};

export const tagSearchClickInduceAlert = () => {
  if (localStorage.getItem("tagSearchClickInduceAlert")) {
    return;
  }
  Alert.info(
    "<p>빨간 태그를 클릭하여</p><p>태그가 포함된 칵테일을 확인해 보세요.</p>",
    loadConfig()
  );
  localStorage.setItem("tagSearchClickInduceAlert", "true");
};

export const recommendResultClickInduceAlert = () => {
  if (localStorage.getItem("recommendResultClickInduceAlert")) {
    return;
  }
  Alert.info(
    "<p>이미지를 클릭하여</p><p>자세한 정보를 확인해 보세요.</p>",
    loadConfig()
  );
  localStorage.setItem("recommendResultClickInduceAlert", "true");
};
