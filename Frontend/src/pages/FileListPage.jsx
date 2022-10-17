// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

import Header from '../components/Header';

import "../style/FileListPage.scss"

export const FileListPage = () => {
    return (
        <>
            <Header/>
            <div className="FileListContainer">
                <div className="FileListHeader">
                    <h1>파일 목록</h1>
                    <Link to="/transcribe/file">
                        <button>
                        파일 업로드
                        </button>
                    </Link>
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