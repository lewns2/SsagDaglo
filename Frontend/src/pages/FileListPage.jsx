// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

import ContentHeader from '../components/ContentHeader';

export const FileListPage = () => {
    return (
        <>
            <ContentHeader/>
            <div>
                <h1>파일 목록</h1>
                <Link to="/transcribe/file">
                    <button>
                    파일 업로드
                    </button>
                </Link>
            </div>
            
            {/* 카테고리 선택란 + 검색 창 */}
            <div>
                <input type="text"></input>
                <button>검색</button>
            </div>
        </>
    )
}

export default FileListPage;