import { useState, useEffect } from 'react';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import * as useFetchFiles from '../apis/FetchFiles';

import '../style/FileListPage.scss';

export const FileListPage = () => {
  const navigate = useNavigate();
  const [fileList, setFileList] = useState();

  useEffect(() => {
    let userNickName = sessionStorage.getItem('userNickName');
    let response = useFetchFiles.reqFiles(userNickName);
    response.then((res) => {
      console.log(res);
      setFileList(res.data.reverse());
    });
  }, []);

  const moveToResult = (num, fileNo) => {
    navigate(`/list/${fileNo}`, { state: { id: fileNo } });
  };

  return (
    <>
      <Header />
      <div className="FileListContainer">
        <div className="FileListHeader">
          <h1>파일 목록</h1>
          <p onClick={() => navigate('/transcribe/file')} style={{ cursor: 'pointer' }}>
            파일 업로드
          </p>
        </div>

        {/* 파일 목록 영역 */}
        <table className="board-table">
          <thead>
            <tr>
              <th scope="col" className="th-num">
                번호
              </th>
              <th scope="col" className="th-title">
                제목
              </th>
              <th scope="col" className="th-date">
                등록일
              </th>
              <th scope="col" className="th-date">
                수정일
              </th>
              <th scope="col" className="th-status">
                상태
              </th>
              <th scope="col" className="th-result">
                결과 보기
              </th>
            </tr>
          </thead>
          <tbody>
            {fileList &&
              fileList.map((file, index) => (
                <tr key={index}>
                  <td>{fileList.length - index}</td>
                  <th>
                    <div>{file[0]}</div>
                  </th>
                  <td>{file[1]}</td>
                  <td>{file[2]}</td>
                  <td>완료</td>
                  <td>
                    <button onClick={() => moveToResult(fileList.length - index, file[3])}>
                      보기
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default FileListPage;
