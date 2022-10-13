// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

export const FileListPage = () => {
    return (
        <>
            <h1>파일 목록</h1>
            <Link to="transcribe/file">
                <button>
                파일 업로드
                </button>
            </Link>
        </>
    )
}

export default FileListPage;