import React, { useState } from "react";
import { Link, Redirect, useHistory } from "react-router-dom";
import { useRecoilValue } from "recoil/dist";
import Alert from "react-s-alert";
import { userState } from "../../recoil";
import { signup } from "../../api";
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from "../../constants";
import "../../css/user/signup.css";

const Signup = (props) => {
  const authenticated = useRecoilValue(userState).authenticated;

  if (authenticated) {
    return (
      <Redirect
        to={{
          pathname: "/",
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
          이미 계정이 있으신가요? <Link to="/login">로그인 하기!!</Link>
        </span>
      </div>
    </div>
  );
};

const SocialSignup = () => {
  return (
    <div className="social-signup">
      <a className="social-btn" href={GOOGLE_AUTH_URL}>
        <img src="/image/google-logo.png" alt="Google" />
        <p>Google로 회원가입하기</p>
      </a>
    </div>
  );
};

const SignupForm = () => {
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
      .then(() => {
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
    <form className="form-container" onSubmit={handleSubmit}>
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
