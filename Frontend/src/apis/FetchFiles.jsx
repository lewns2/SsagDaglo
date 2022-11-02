import axios from 'axios';

const baseURL = 'http://localhost:8080';
// const baseURL = 'https://api.ssagdaglo.cf';

// 1. 해당 유저 파일 목록 조회
const reqFiles = async (userNickName, selectedPage) => {
  let result;
  try {
    await axios
      .get(`${baseURL}/list/findAll/${userNickName}?page=${selectedPage - 1}&size=7`)
      // .get(`${baseURL}/list?page=1&size=5sort=DES&nickname=${}`)
      // .get(`${baseURL}/list/findAll?page=1&size=5&nickName=${userNickName}`)
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
