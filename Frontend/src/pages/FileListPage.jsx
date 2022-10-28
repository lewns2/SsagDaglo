import { useState, useEffect } from 'react';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import * as useFetchFiles from "../apis/FetchFiles";

import "../style/FileListPage.scss"

export const FileListPage = () => {

    const navigate = useNavigate();
    const [fileList, setFileList] = useState();

    useEffect(() => {
        let userNickName = sessionStorage.getItem('userNickName');
        let response = useFetchFiles.reqFiles(userNickName);
        response.then((res) => {
            // console.log(res.data);
            setFileList(res.data.reverse());
        })
    }, []);

    return (
        <>
            <Header/>
            <div className="FileListContainer">
                <div className="FileListHeader">
                    <h1>파일 목록</h1>
                    <p onClick={() => navigate('/transcribe/file')} style={{cursor:'pointer'}}>파일 업로드</p>
                </div>
                
                {/* 카테고리 선택란 + 검색 창 */}
                <div className="FileListTool">
                    <input type="text"></input>
                    <button>검색</button>
                </div>
            
            {/* <button onClick={() => testApi()}>테에에에에스으으으으트으으으으</button> */}
            
            {/* 파일 목록 영역 */}
            <table className="board-table">
                <thead>
                <tr>
                    <th scope="col" className="th-num">번호</th>
                    <th scope="col" className="th-title">제목</th>
                    <th scope="col" className="th-date">등록일</th>
                    <th scope="col" className="th-date">수정일</th>
                    <th scope="col" className="th-status">상태</th>
                    <th scope="col" className="th-result">결과 보기</th>
                </tr>
                </thead>
                <tbody>
                {
                    fileList && fileList.map((file, index) => (
                        <tr key={index}>
                            <td>{fileList.length - index}</td>
                            <th>
                                <a href="#!">{file[0]}</a>
                            </th>
                            <td>{file[1]}</td>
                            <td>{file[2]}</td>
                            <td>완료</td>
                            <td><button>보기</button></td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
            </div>
        </>
    )
}

export default FileListPage;