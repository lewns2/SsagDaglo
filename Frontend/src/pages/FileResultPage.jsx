import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ReactPlayer from 'react-player';

/* API 모듈 */
import * as useFetchFiles from '../apis/FetchFiles';

/* 공통 컴포넌트 및 스타일 */
import Header from '../components/Header';
import Container from '../components/Container';
import Wrapper from '../components/Wrapper';
import Lottie from '../components/Lottie';
import '../style/FileResultPage.scss';
import { Editor } from "@toast-ui/react-editor";
import '@toast-ui/editor/dist/toastui-editor.css';

export const FileResultPage = () => {
  const loaction = useLocation();

  const [fileNum, setFileNum] = useState();
  const [fileTitle, setFileTitle] = useState();
  const [isPending, setIsPending] = useState(false);
  const [fileResult, setFileResult] = useState();
  const [fileSummary, setFileSummary] = useState();
  const [videoUrl, setVideoUrl] = useState();
  const [viewStatus, setViewStatus] = useState(0)

  useEffect(() => {
    /* 상세 파일 데이터 요청 */
    setFileNum(loaction.state.id);
    setFileTitle(loaction.state.title);
    console.log(loaction.state);
    let response = useFetchFiles.reqFileInfo(loaction.state.id);
    response.then((res) => {
      console.log(res);
      setIsPending(true);
      setFileResult(res.data.script);
      setFileSummary(res.data.summary);
      setVideoUrl(res.data.youtubeUrl);
    });
  }, []);

  const toggleView = () => {
    console.log(viewStatus);
    setViewStatus(viewStatus^1);
  }

  return (
    <>
      <Container>
        <Wrapper>
          <div className="fileResultContainer">
            {isPending ? (
              <>
              <h1 style={{marginTop: "7%"}}>{fileTitle}</h1>
              <div style={{
                display: "flex",
                justifyContent: "center",
                marginBottom: "20px",
                marginTop: "30px"
                }}>
                  
                <div onClick={() => toggleView()} style={{cursor:"pointer"}}>{!viewStatus ? "요약보기": "대본보기"}</div>
              </div>
              {!viewStatus ? <div className="fileResultContent" style={{whiteSpace : "pre-wrap", overflow:"auto", width:"100%", height:"70vh"}}><div style={{width:"90%", margin:"auto"}}>{fileResult}</div></div>:<div style={{whiteSpace : "pre-wrap", overflow:"auto", width:"100%", height:"70vh"}}><div style={{width:"90%", margin:"auto"}}>{fileSummary}</div></div>}
                <div
                  style={{
                    position: 'fixed',
                    left: '0',
                    bottom: '0',
                  }}>
                  <ReactPlayer
                    url={videoUrl}
                    width="300px"
                    height="200px"
                    playing={false}
                    muted={true}
                    controls={true}></ReactPlayer>
                </div>
              </>
            ) : (
              <>
                <Header/>
                <p>결과를 불러오고 있어요!</p>
                <div style={{ maxWidth: '70%', display: 'flex', margin: 'auto' }}>
                  <LoadingLottie />
                </div>
              </>
            )}
          </div>
        </Wrapper>
      </Container>
    </>
  );
};

const LoadingLottie = (props) => (
  <Lottie {...props} src="https://assets10.lottiefiles.com/packages/lf20_x62chJ.json" />
);

export default FileResultPage;
