import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

/* API 모듈 */
import * as useFetchFiles from '../apis/FetchFiles';

/* 공통 컴포넌트 및 스타일 */
import Header from '../components/Header';
import Container from '../components/Container';
import Lottie from '../components/Lottie';
import '../style/FileResultPage.scss';

export const FileResultPage = () => {
  const loaction = useLocation();

  const [fileNum, setFileNum] = useState();
  const [isPending, setIsPending] = useState(false);
  const [fileResult, setFileResult] = useState();

  useEffect(() => {
    /* 상세 파일 데이터 요청 */ 
    setFileNum(loaction.state.id);
    let response = useFetchFiles.reqFileInfo(loaction.state.id);
    response.then((res) => {
      console.log(res);
      setIsPending(true);
      setFileResult(res.data);
    })
  }, []);

  return (
    <>
      <Header />
      <Container>
        <div className="fileResultContainer">
          <h1>결과물</h1>
          {isPending ? (
            <>
            <div>파일번호 : {fileNum}</div>
            <div className="fileResultContent">
              {fileResult}
            </div>
            </>            
          ) : (
            <LoadingLottie />
          )}
          
        </div>
      </Container>
    </>
  );
};

const LoadingLottie = (props) => (
  <Lottie
    {...props}
    src= "https://assets10.lottiefiles.com/packages/lf20_x62chJ.json"
  />
);


export default FileResultPage;
