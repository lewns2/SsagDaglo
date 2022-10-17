import axios from 'axios';
import { useState, useEffect } from 'react';

import Header from '../components/Header';

import "../style/FileUploadPage.scss"


export const FileUploadPage = () => {
    const REACT_APP_YOUTUBE_API_KEY = process.env.REACT_APP_YOUTUBE_API_KEY;

    const [selectedUploadType, setSelectedUploadType] = useState('file');
    const [givenAudio, setGivenAudio] = useState();
    const [isVisible, setIsVisible] = useState(false);
    const [givenYoutubeLink, setGivenYoutubeLink] = useState('');

    const clickTypeButton = (type) => {
        if(type === 'file') setSelectedUploadType('file')
        else if(type === 'link') {
            setSelectedUploadType('link');
            setGivenAudio('');
            setIsVisible(false);   
        }
    }

    const onChangeAudio = (e) => {
        if(e.target.files[0]) {
            setGivenAudio(URL.createObjectURL(e.target.files[0]));
            setIsVisible(true);
        }
    }

    const onChangeLink = (e) => {
        setGivenYoutubeLink(e.target.value);
    }

    const handlePlay = (isPlay) => {
        let playAudio = new Audio(givenAudio);
        if(givenAudio && isPlay) {
            playAudio.play();
        }
        else {
            playAudio.pause();
            playAudio = null;
        }
    }

    const handleRequest = () => {
        console.log(selectedUploadType)
        if(selectedUploadType === 'file') {
            // 파일 axios 요청 + 요청 보낼 파일이 없으면 다음 비활성화
            console.log(givenAudio)
        }
        else if(selectedUploadType === 'link') {
            // 유튜브 axios 요청 + 유효성 검사 필요
            console.log(givenYoutubeLink)
        }
    }

    const getYoutubeInfo = () => {
        console.log(REACT_APP_YOUTUBE_API_KEY)
        axios.get(`https://www.googleapis.com/youtube/v3/search?part=snippet&q=kpop+music&key=${REACT_APP_YOUTUBE_API_KEY}`)
        .then((res) => {
            console.log(res);
        })
        .catch((err) => console.log(err));
    }

    useEffect(() => {
        // console.log(selectedUploadType);
        // console.log(givenAudio);
        // console.log(givenYoutubeLink);
    }, [selectedUploadType, givenAudio, givenYoutubeLink])

    

    return (
        <>  
            <Header/>
            <div className="FileUploadContainer">
            {/* 업로드 타입 선택 카테고리 */}
            <h1>파일 업로드</h1>
            <div className = "uploadType">
                <div>
                    <button
                    onClick={() => clickTypeButton('file')}
                    >파일 업로드
                    </button>
                    
                </div>
                <div>
                <button
                    onClick={() => clickTypeButton('link')}
                    >유튜브 링크
                    </button>
                </div>
            </div>

            {/* 타입에 알맞은 파일 업로드 방법 */}
            <div className = "uploadObject">
                { selectedUploadType === 'file' ? 
                    (<div className = "audioFile">
                        <input type="file" accept="audio/*" 
                            onChange={onChangeAudio}/>

                        <div className = "audioController" style={{display: isVisible ? '' : 'none'}}>
                            <button onClick={() => handlePlay(true)}>Play</button>
                            <button onClick={() => handlePlay(false)}>Pause</button>
                        </div>
                    </div>
                    
                    ) 
                        : 
                    (
                    <div className = "youtubeLink">
                        <input placeholder='예) www.youtube.com/watch?v=4VrNZzzOldc'
                            onChange={onChangeLink}
                        />
                        <button onClick={() => getYoutubeInfo()}>링크 등록</button>
                    </div>
                    )
                }

            </div>

            {/* 업로드가 성공적인 경우를 확인하고, 다음 버튼 활성화 */}
            <button onClick={()=>handleRequest()}>다음</button>
            </div>
        </>
    )
}

export default FileUploadPage;