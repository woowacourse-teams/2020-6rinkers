import { ACCESS_TOKEN, API_BASE_URL } from "../constants";

const request = (options) => {
  const headers = new Headers({
    "Content-Type": "application/json",
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      "Authorization",
      `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    );
  }

  const defaults = { headers };
  options = { ...defaults, ...options };

  return fetch(options.url, options).then((response) =>
    response.json().then((json) => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

export const getCurrentUser = () => {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: `${API_BASE_URL}/user/me`,
    method: "GET",
  });
};

export const login = (loginRequest) => {
  return request({
    url: `${API_BASE_URL}/auth/login`,
    method: "POST",
    body: JSON.stringify(loginRequest),
  });
};

export const signup = (signupRequest) => {
  return request({
    url: `${API_BASE_URL}/auth/signup`,
    method: "POST",
    body: JSON.stringify(signupRequest),
  });
};
