import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Header from '../components/Header';

import * as useFetchFiles from '../apis/FetchFiles';

export const FileResultPage = () => {
  const loaction = useLocation();

  const [fileNum, setFileNum] = useState();

  useEffect(() => {
    // 상세 파일 데이터 요청
    setFileNum(loaction.state.id);
    useFetchFiles.reqFileInfo(fileNum);
  }, [fileNum]);

  return (
    <>
      <Header />
      <h1>결과물</h1>
      <div>파일번호 : {fileNum}</div>
    </>
  );
};

export default FileResultPage;
