import axios from "axios"

// const baseURL = 'http://172.27.162.23:8080'
const baseURL = 'http://localhost:8080'

const header = { headers: { 'Content-Type': 'multipart/form-data' }};

// 1. 오디오 파일 업로드 요청 API
const reqUploadAudio = async (data) => {
    try {   
          await axios.post(`${baseURL}/upload`, data, header)
          .then((res) => console.log(res))
          .catch((err)=> console.log(err));
    } catch (error) {
        console.log("오디오 요청 에러 : ", error);
        return error;
    }
}

// 2. 유튜브 동영상 정보 요청 API
const reqYoutubeInfos = async (data) => {
    
    let result = null;

    try {
        await axios.get(`https://www.googleapis.com/youtube/v3/videos?part=snippet&id=${data}&key=${process.env.REACT_APP_YOUTUBE_API_KEY}`)
        .then((res) => {
            result = res.data.items[0].snippet;
            // console.log(res.data.items[0].snippet);
        })
        
        .catch((err) => console.log(err));

        if (result !== null) {
            return result;
        }

    } catch (error) {
        console.log("유튜브 요청 에러, ", error);
    }
}

export { reqUploadAudio, reqYoutubeInfos};