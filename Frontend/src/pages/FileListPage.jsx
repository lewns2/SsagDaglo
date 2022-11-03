import { useState, useEffect } from 'react';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import * as useFetchFiles from '../apis/FetchFiles';
import Alert from '../components/Alert';

import '../style/FileListPage.scss';

export const FileListPage = () => {
  const navigate = useNavigate();
  const [fileList, setFileList] = useState();
  const [totalPages, setTotalPages] = useState();
  const [selectedPage, setSelectedPage] = useState(0);

  // 유저 파일 목록 조회 + 페이지 처리
  useEffect(() => {
    let userNickName = sessionStorage.getItem('userNickName');
    let response = useFetchFiles.reqFiles(userNickName, selectedPage);
    response.then((res) => {
      console.log(res);
      setFileList(res.data.fileInfo);
      setTotalPages(res.data.totalPages);
    });
  }, [selectedPage]);

  // 결과 페이지 이동 함수
  const moveToResult = (fileNo) => {
    navigate(`/list/${fileNo}`, { state: { id: fileNo } });
  };

  // 페이징 처리 구현 함수
  const renderPagiantion = (tot) => {
    const result = [];
    for (let i = 1; i <= tot; i++) {
      result.push(
        <li key={i} className="page-item">
          <a
            href={() => false}
            className="page-link"
            onClick={() => setSelectedPage(i)}
            aria-current={selectedPage === i ? 'selectedPage' : null}>
            {i}
          </a>
        </li>,
      );
    }
    return result;
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
              <th scope="col" className="th-tiçtle">
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
                  <td>{file[3]}</td>
                  <th>
                    <div
                      style={{
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap',
                        wordBreak: 'break-all',
                        maxWidth: '200px',
                      }}>
                      {file[0]}
                    </div>
                  </th>
                  <td>{file[1]}</td>
                  <td>{file[2]}</td>
                  <td>완료</td>
                  <td>
                    <button onClick={() => moveToResult(file[3])}>
                      보기
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>

        {/* 페이지네이션 */}
        <div className="paginationWrap">
          <ul className="pagination">
            <li className="page-item">
              <a
                href={() => false}
                className="page-link"
                aria-label="Previous"
                onClick={() =>
                  selectedPage !== 1
                    ? setSelectedPage(selectedPage - 1)
                    : Alert(false, '첫번째 페이지입니다.')
                }>
                <span aria-hidden="true">&lt;</span>
              </a>
            </li>

            {totalPages != null ? renderPagiantion(totalPages) : null}

            <li className="page-item">
              <a
                href={() => false}
                className="page-link"
                aria-label="Next"
                onClick={() =>
                  selectedPage !== totalPages
                    ? setSelectedPage(selectedPage + 1)
                    : Alert(false, '마지막 페이지입니다.')
                }>
                <span aria-hidden="true">&gt;</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </>
  );
};

export default FileListPage;
