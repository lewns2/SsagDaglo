// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

export const FileListPage = () => {
    return (
        <>
            <div>
                <h1>파일 목록</h1>
                <Link to="transcribe/file">
                    <button>
                    파일 업로드
                    </button>
                </Link>
            </div>
        </>
    )
}

export default FileListPage;