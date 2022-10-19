import axios from "axios"

// const baseURL = 'http://localhost:8080'
// const baseURL = 'http://172.27.162.23:8080'

const reqSignup = async (data) => {    
    try {
        // console.log("여기 : ", data)
        const { response } = await axios.post('http://172.27.162.23:8080/signup', data);
        return response;

    } catch (error) {
        console.log(error);
        return error;
    }
}

const reqLogin = async (data) => {
    try {
        console.log("여기 : ", data)
        const { response } = await axios.post(`http://172.27.162.23:8080/login`, data).then((res) => console.log(res));
        // console.log(response);
        return response;
        
    } catch (error) {
        console.log(error);
        return error;
    }
}

export { reqSignup, reqLogin };