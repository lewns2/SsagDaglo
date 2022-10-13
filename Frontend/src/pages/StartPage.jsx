// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

export const StartPage = () => {
    return (
        <>
            <h1>시작페이지</h1>
            <Link to="/list">
                <button>
                시작하기
                </button>
            </Link>
            <Link to="login">
                로그인
            </Link>
            <Link to="signup">
                회원가입
            </Link>
        </>
    )
}

export default StartPage;