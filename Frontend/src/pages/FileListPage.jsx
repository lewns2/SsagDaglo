// import { useState, useEffect } from 'react';
import Header from '../components/Header';
import { useNavigate } from 'react-router-dom';
import * as useFetchFiles from "../apis/FetchFiles";

import "../style/FileListPage.scss"
import { useEffect } from 'react';

export const FileListPage = () => {

    const navigate = useNavigate();

    useEffect(() => {
        let userNickName = sessionStorage.getItem('userNickName');
        useFetchFiles.reqFiles(userNickName);
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
            </div>
        </>
    )
}

export default FileListPage;