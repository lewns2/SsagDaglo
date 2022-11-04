import axios from 'axios';

// const baseURL = 'http://localhost:8080';
const baseURL = 'https://api.ssagdaglo.cf';

// 1. 해당 유저 파일 목록 조회
const reqFiles = async (userNickName, selectedPage) => {
  let result;
  try {
    await axios
      .get(`${baseURL}/list/findAll/${userNickName}?page=${selectedPage - 1}&size=7`)
      .then((res) => (result = res.data))
      .catch((err) => console.log(err));
  } catch {
    console.log('파일 목록 조회 에러');
  }

  return result;
};

// 2. 파일에 대한 결과 요청
const reqFileInfo = async (fileNum) => {
  let result;
  try {
    await axios
      .get(`${baseURL}/list/${fileNum}`)
      .then((res) => {
        console.log(res);
        (res.data.status === "sucess" ? result = res.data : result = false);
      })
      .catch((err) => console.log(err));
  } catch {
    console.log('파일 결과 요청 에러');
  }
  return result;
};

export { reqFiles, reqFileInfo };
