import axios from 'axios';

const baseURL = 'http://localhost:8080';

// 1. 해당 유저 파일 목록 조회
const reqFiles = async (userNickName) => {
  let result;
  try {
    await axios
      .get(`${baseURL}/list/${userNickName}`)
      .then((res) => (result = res.data))
      .catch((err) => console.log(err));
  } catch {
    console.log('파일 목록 조회 에러');
  }

  return result;
};

// 2. 파일에 대한 결과 요청
const reqFileInfo = async () => {
  let result;
  try {
  } catch {
    console.log('파일 결과 요청 에러');
  }
};

export { reqFiles, reqFileInfo };
