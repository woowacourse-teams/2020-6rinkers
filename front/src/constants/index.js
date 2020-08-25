export const DOWN = 40;
export const UP = 38;
export const ENTER = 13;
export const ESC = 27;

export const USER_PROTOTYPE = {
  id: 0,
  name: "",
  email: "",
  imageUrl: "",
  provider: "",
  providerId: "",
  role: "",
  roleName: "",
};

export const ACCESS_TOKEN = "accessToken";
export const API_BASE_URL = `//${process.env.REACT_APP_HOST}`;
export const OAUTH2_REDIRECT_URI = "http://localhost:3000/oauth2/redirect";
export const GOOGLE_AUTH_URL = `${API_BASE_URL}/oauth2/authorize/google?redirect_uri=${OAUTH2_REDIRECT_URI}`;
export const FACEBOOK_AUTH_URL = `${API_BASE_URL}/oauth2/authorize/facebook?redirect_uri=${OAUTH2_REDIRECT_URI}`;
