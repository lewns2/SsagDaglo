import axios from "axios"

const baseURL = 'http://localhost:8080'


// 1. 해당 유저 파일 목록 조회
const reqFiles = async(userNickName) => {
    try {
        await axios.get(`${baseURL}/list/${userNickName}`)
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    }
    catch {
        console.log("파일 목록 조회 에러");
    }
    
    return;
}


export { reqFiles };