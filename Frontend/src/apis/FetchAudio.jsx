import axios from "axios"

const baseURL = 'http://172.27.162.23:8080'

const header = { headers: { 'Content-Type': 'multipart/form-data' }};

// 1. 오디오 파일 업로드 요청 API
const reqUploadAudio = async (data) => {
    try {   
          axios.post(`${baseURL}/upload`, data, header)
          .then((res) => console.log(res))
          .catch((err)=> console.log(err));

    } catch (error) {
        console.log("오디오 요청 에러 : ", error);
        return error;
    }
}

export { reqUploadAudio };