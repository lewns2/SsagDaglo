import axios from "axios"

const baseURL = 'http://172.27.162.23:8080'

// 회원 가입 요청
const reqSignup = async (data) => {    

    try {
        let result;

        await axios.post(`${baseURL}/signup`, data)
        .then((res) => res.data === true? result = true : result = false); 
        return result;

    } catch (error) {
        console.log("회원가입 에러 : ", error);
        return error;
    }
}

// 로그인 요청
const reqLogin = async (data) => {
    try {
        let result;
        await axios.post(`${baseURL}/login`, data)
        .then((res) =>{
            result = res.data;
        }); 
        return result;
        
    } catch (error) {
        console.log("로그인 에러", error);
        return error;
    }
}

// 닉네임 중복 확인
const reqCheckNickName = async (data) => {

    try {
        let result;

        await axios.post(`${baseURL}/chknickname`, data, {headers : {'Content-Type': 'application/json'}})
        .then((res) => res.data === true? result = true : result = false); 
        return result;
        
    } catch (error) {
        console.log("닉네임 중복 확인 에러", error);
        return error;
    }
}

// 이메일 중복 확인
const reqCheckEmail = async (data) => {
    try {
        let result;

        await axios.post(`${baseURL}/chkemail`, data, {headers : {'Content-Type': 'application/json'}})
        .then((res) => res.data ? result = true : result = false); 
        return result;
        
    } catch (error) {
        console.log("이메일 중복 확인 에러", error);
        return error;
    }
}
export { reqSignup, reqLogin, reqCheckNickName, reqCheckEmail};