import axios from 'axios';

const baseURL = 'http://localhost:8080';
// const baseURL = 'https://api.ssagdaglo.cf';

// 1. 회원 가입 요청 API
const reqSignup = async (data) => {
  try {
    let result;

    await axios.post(`${baseURL}/user/signup`, data).then((res) => {
      res.data.status === 'sucess' ? (result = true) : (result = false);
    });

    return result;
  } catch (error) {
    console.log('회원가입 에러 : ', error);
    return error;
  }
};

// 2. 로그인 요청 API
const reqLogin = async (data) => {
  try {
    let result;
    await axios.post(`${baseURL}/user/login`, data).then((res) => {
      // console.log(res.data);
      result = res.data.status;
      if (res.data.status === 'sucess') {
        sessionStorage.setItem('userNickName', res.data.data);
      }
    });
    return result;
  } catch (error) {
    console.log('로그인 에러', error);
    return error;
  }
};

// 3. 닉네임 중복 확인 API
const reqCheckNickName = async (data) => {
  try {
    let result;

    await axios
      .post(`${baseURL}/user/chknickname`, data, {
        headers: { 'Content-Type': 'application/json' },
      })
      .then((res) => {
        res.data.status === 'sucess' ? (result = true) : (result = false);
      });
    return result;
  } catch (error) {
    console.log('닉네임 중복 확인 에러', error);
    return error;
  }
};

// 4. 이메일 중복 확인 API
const reqCheckEmail = async (data) => {
  try {
    let result;

    await axios
      .post(`${baseURL}/user/chkemail`, data, { headers: { 'Content-Type': 'application/json' } })
      .then((res) => {
        res.data.status === 'sucess' ? (result = true) : (result = false);
      });
    return result;
  } catch (error) {
    console.log('이메일 중복 확인 에러', error);
    return error;
  }
};
export { reqSignup, reqLogin, reqCheckNickName, reqCheckEmail };
