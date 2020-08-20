import React, { useState } from "react";
import { Link, Redirect, useHistory, useLocation } from "react-router-dom";
import Alert from "react-s-alert";
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from "../../constants";
import { signup } from "../../utils/APIUtils";
import "../../css/user/signup.css";

const Signup = (props) => {
  const location = useLocation();
  if (props.authenticated) {
    return (
      <Redirect
        to={{
          pathname: "/",
          state: { from: location.pathname },
        }}
      />
    );
  }

  return (
    <div className="signup-container">
      <div className="signup-content">
        <SocialSignup />
        <div className="or-separator">
          <span className="or-text">OR</span>
        </div>
        <SignupForm {...props} />
        <span className="login-link">
          Already have an account? <Link to="/login">Login!</Link>
        </span>
      </div>
    </div>
  );
};

const SocialSignup = (props) => {
  return (
    <div className="social-signup">
      <a className="social-btn" href={GOOGLE_AUTH_URL}>
        <img src="/image/google-logo.png" alt="Google" />
        <p>Google로 로그인하기</p>
      </a>
      <a className="social-btn" href={FACEBOOK_AUTH_URL}>
        <img src="/image/fb-logo.png" alt="Facebook" />
        <p>Facebook으로 로그인하기</p>
      </a>
    </div>
  );
};

const SignupForm = (props) => {
  const [inputs, setInputs] = useState({
    name: "",
    email: "",
    password: "",
  });

  const { name, email, password } = inputs;

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

    const signUpRequest = { ...inputs };

    signup(signUpRequest)
      .then((response) => {
        Alert.success("회원 가입이 완료되었습니다.");
        history.push("/login");
      })
      .catch((e) => {
        Alert.error(
          (e && e.message) || "회원 가입에 실패했습니다. 다시 시도해주세요."
        );
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-item">
        <input
          type="text"
          name="name"
          className="form-control"
          placeholder="이름"
          value={name}
          onChange={onChange}
          required
        />
      </div>
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
          회원가입
        </button>
      </div>
    </form>
  );
};

export default Signup;
