import React, { useEffect, useState } from "react";
import { Link, Redirect, useHistory, useLocation } from "react-router-dom";
import Alert from "react-s-alert";
import {
  ACCESS_TOKEN,
  FACEBOOK_AUTH_URL,
  GOOGLE_AUTH_URL,
} from "../../constants";
import { login } from "../../utils/APIUtils";
import "../../css/user/login.css";

const Login = (props) => {
  const location = useLocation();
  const history = useHistory();

  useEffect(() => {
    if (location.state && location.state.error) {
      setTimeout(() => {
        Alert.error(location.state.error, {
          timeout: 5000,
        });
        history.replace({
          pathname: location.pathname,
          state: {},
        });
      }, 100);
    }
  });

  if (props.authenticated) {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: props.location },
        }}
      />
    );
  }
  return (
    <div className="login-container">
      <div className="login-content">
        <SocialLogin />
        <div className="or-separator">
          <span className="or-text">OR</span>
        </div>
        <LoginForm {...props} />
        <span className="signup-link">
          New user? <Link to="/signup">Sign up!</Link>
        </span>
      </div>
    </div>
  );
};

function SocialLogin() {
  return (
    <div className="social-login">
      <a className="social-btn" href={GOOGLE_AUTH_URL}>
        <img src="image/google-logo.png" alt="Google" />
        <p>Google로 로그인하기</p>
      </a>
      <a className="social-btn" href={FACEBOOK_AUTH_URL}>
        <img src="image/fb-logo.png" alt="Facebook" />
        <p>Facebook으로 로그인하기</p>
      </a>
    </div>
  );
}

const LoginForm = () => {
  const [inputs, setInputs] = useState({
    email: "",
    password: "",
  });

  const { email, password } = inputs;

  const onChange = (e) => {
    const { value, name } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();

    const loginRequest = { ...inputs };

    login(loginRequest)
      .then((response) => {
        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        Alert.success("로그인되었습니다.");
        history.push("/");
      })
      .catch((error) => {
        Alert.error(
          (error && error.message) ||
            "로그인에 실패하였습니다. 다시 시도해주세요."
        );
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-item">
        <input
          type="email"
          name="email"
          className="form-control"
          placeholder="이메일"
          value={email}
          onChange={onChange}
          required
        />
      </div>
      <div className="form-item">
        <input
          type="password"
          name="password"
          className="form-control"
          placeholder="비밀번호"
          value={password}
          onChange={onChange}
          required
        />
      </div>
      <div className="form-item">
        <button type="submit" className="btn-primary">
          로그인
        </button>
      </div>
    </form>
  );
};

export default Login;
