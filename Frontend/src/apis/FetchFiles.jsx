import axios from "axios"

const baseURL = 'http://localhost:8080'


// 1. 해당 유저 파일 목록 조회
const reqFiles = async(userNickName) => {
    let result;
    try {
        await axios.get(`${baseURL}/list/${userNickName}`)
        .then((res) => result = res.data)
        .catch((err) => console.log(err));
    }
    catch {
        console.log("파일 목록 조회 에러");
    }
    
    return result;
}

// 2. 다른 조 테스트용
const reqTest = async() => {
    try {
        await axios.post('http://172.27.167.16:5000/postrecommendapikey', {'movie_name' : 'Star Wars'})
        .then((res) => console.log(res))
        .catch((err) => console.log(err));
    }
    catch {
        console.log("파일 목록 조회 에러");
    }
    
    return;
}



export { reqFiles, reqTest };