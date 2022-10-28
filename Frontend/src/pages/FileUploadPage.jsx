import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Header from '../components/Header';
import * as useFetchAudio from '../apis/FetchAudio';
import Alert from '../components/Alert';

import "../style/FileUploadPage.scss"
import { get } from 'react-hook-form';


export const FileUploadPage = () => {

    const navigate = useNavigate();

    const [selectedUploadType, setSelectedUploadType] = useState('file');
    const [uploadAudio, setUploadAudio] = useState();
    const [isVisible, setIsVisible] = useState(false);
    const [givenYoutubeLink, setGivenYoutubeLink] = useState('');
    const [youtubeVidInfos, setYoutubeVidInfos] = useState({
        title : '',
        channelTitle : '',
        thumbnails : '',
    });
    const [testAudio, setTestAudio] = useState();

    // [UX] 카테고리 선택에 따라 보여줄 화면을 핸들링할 데이터 
    const clickTypeButton = (type) => {
        if(type === 'file') setSelectedUploadType('file')
        else if(type === 'link') {
            setSelectedUploadType('link');
            setUploadAudio('');
            setIsVisible(false);   
        }
    }

    // [기능 / UX] 업로드 파일, 변수에 저장 및 음악 재생 기능
    const onChangeAudio = (e) => {
        if(e.target.files[0]) {
            setUploadAudio(e.target.files[0]);
            setIsVisible(true);
        }
    }

    const onChangeLink = (e) => {
        setGivenYoutubeLink(e.target.value);
    }

    const handlePlay = (isPlay) => {
        let playAudio = new Audio(uploadAudio);
        if(uploadAudio && isPlay) {
            playAudio.play();
        }
        else {
            playAudio.pause();
            playAudio = null;
        }
    }

    // [기능] 파일 업로드 후, 요청 시 서버에 보내줄 form 형태 변환 및 API 요청 / [다음] 버튼 클릭
    const handleRequest = () => {

        // 음성 파일 업로드
        if(selectedUploadType === 'file') {
            const formData = new FormData();
            formData.append(
                'key',
                new Blob([JSON.stringify({'userNickname' : sessionStorage.getItem('userNickName')})], {
                type: 'application/json',
                }),
            );
            formData.append('file', uploadAudio);
            for (const keyValue of formData) console.log(keyValue); 
            let response = useFetchAudio.reqUploadAudio(formData);
            response.then((res) => {if(res === 200) navigate('/list')});
        }
        
        // 유튜브 링크 전송
        else if(selectedUploadType === 'link') {
            console.log(givenYoutubeLink)
        }
    }


    // [기능] 유튜브 url 중 video id 파싱 및 api 요청
    const getVideoID = () => {

        if(!givenYoutubeLink) {
            Alert(false, '비디오 링크를 입력해주세요!')
        }

        else {
            let url = givenYoutubeLink.split(/(vi\/|v=|\/v\/|youtu\.be\/|\/embed\/)/);
            if(url[2] !== undefined) {
                url = url[2].split(/[^0-9a-z_-]/i)[0];
                
                let response = useFetchAudio.reqYoutubeInfos(url);  
                response.then((res) => {
                    setYoutubeVidInfos({title: res.title, channelTitle: res.channelTitle, thumbnails: res.thumbnails.standard.url });
                })
            }
            else {
                Alert(false, '올바른 비디오 링크를 입력해주세요!');
            }    
        }
    }   

    const getAudioFromYoutubeLink = () => {
        fetch("https://images" + ~~(Math.random() * 33) + "-focus-opensocial.googleusercontent.com/gadgets/proxy?container=none&url=" + encodeURIComponent(givenYoutubeLink))
        .then((response) => {
            console.log(response)
            var audio_streams = {};

            response.text().then((data) => {
                var regex = /(?:ytplayer\.config\s*=\s*|ytInitialPlayerResponse\s?=\s?)(.+?)(?:;var|;\(function|\)?;\s*if|;\s*if|;\s*ytplayer\.|;\s*<\/script)/gmsu;

                data = data.split('window.getPageData')[0];
                data = data.replace('ytInitialPlayerResponse = null', '');
                data = data.replace('ytInitialPlayerResponse=window.ytInitialPlayerResponse', '');
                data = data.replace('ytplayer.config={args:{raw_player_response:ytInitialPlayerResponse}};', '');

                var matches = regex.exec(data);
                var data = matches && matches.length > 1 ? JSON.parse(matches[1]) : false;

                console.log(data);

                var streams = [],
                result = {};
      
              if (data.streamingData) {
      
                if (data.streamingData.adaptiveFormats) {
                  streams = streams.concat(data.streamingData.adaptiveFormats);
                }
      
                if (data.streamingData.formats) {
                  streams = streams.concat(data.streamingData.formats);
                }
      
              } else {
                return false;
              }
      
              streams.forEach(function(stream, n) {
                    var itag = stream.itag * 1,
                    quality = false;
                    console.log(stream);
                    switch (itag) {
                    case 139:
                        quality = "48kbps";
                        break;
                    case 140:
                        quality = "128kbps";
                        break;
                    case 141:
                        quality = "256kbps";
                        break;
                    case 249:
                        quality = "webm_l";
                        break;
                    case 250:
                        quality = "webm_m";
                        break;
                    case 251:
                        quality = "webm_h";
                        break;
                        default:
                            break;
                    }
                    if (quality) audio_streams[quality] = stream.url;
                });
        
                console.log(audio_streams);
        
                let audioSrc = audio_streams['256kbps'] || audio_streams['128kbps'] || audio_streams['48kbps'];
                setTestAudio(audioSrc);
                console.log(audioSrc);
                // audio_tag.play();
            })
            
        });
        
    }


    useEffect(() => {
        // console.log(youtubeVidInfos);
    }, [selectedUploadType, uploadAudio, givenYoutubeLink, youtubeVidInfos])

    

    return (
        <>  
            <Header/>
            <div className="FileUploadContainer">
            {/* 업로드 타입 선택 카테고리 */}
            <h1>파일 업로드</h1>
            <div className = "uploadType">
                <div>
                    <p onClick={() => clickTypeButton('file')}>파일 업로드</p>
                </div>
                <div>
                    <p onClick={() => clickTypeButton('link')}>유튜브 링크</p>
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
                        <p>첨부파일은 최대 1개, 30MB까지 등록 가능합니다.</p>
                    </div>
                    
                    ) 
                        : 
                    (
                    <>
                    <div className = "youtubeLinkWrapper">
                        <input
                            className="youtubeInput" 
                            placeholder='예) www.youtube.com/watch?v=4VrNZzzOldc'
                            onChange={onChangeLink}
                        />
                        <button className="youtubeBtn" onClick={() => getVideoID()}>링크 등록</button>
                        <button onClick={() => getAudioFromYoutubeLink()}>음성 파일 추출</button>
                    </div>
                    
                    <div className='youtubeResult'>
                        { youtubeVidInfos.title !== "" ? (
                        <>
                            <div>
                                <img src={youtubeVidInfos.thumbnails ? youtubeVidInfos.thumbnails : ''} alt="썸네일"></img>
                            </div>
                            <div>
                                <p>제목 : {youtubeVidInfos.title ? youtubeVidInfos.title : ''}</p>
                                <p>작성자 : {youtubeVidInfos.channelTitle ? youtubeVidInfos.channelTitle : ''}</p>
                            </div>
                            <audio controls src={testAudio}></audio>
                        </>
                        ) : (
                            <div> 
                            </div>
                        )}
                        
                    </div>
                    </>
                    )
                }

            </div>

            {/* 업로드가 성공적인 경우를 확인하고, 다음 버튼 활성화 */}
            <div className='moveButtons'>
                <p onClick={() => navigate(-1)} style={{cursor:'pointer'}}>이전</p>
                <p onClick={()=>handleRequest()} style={{cursor:'pointer'}}>다음</p>
            </div>
            </div>
        </>
    )
}

export default FileUploadPage;