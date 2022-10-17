// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';
import ContentHeader from '../components/ContentHeader';

export const LoginPage = () => {
    return (
        <>
            <ContentHeader/>

            <h1>로그인</h1>
            <div>
                <input type="text" placeholder="이메일" />
            </div>
            <div>
                <input type="password" placeholder="비밀번호" />
            </div>
            <button>로그인</button>
            <Link to="/signup">
                <p>회원가입</p>
            </Link>
        </>
    )
}

export default LoginPage;