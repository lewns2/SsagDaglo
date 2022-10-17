// import { useState, useEffect } from 'react';
// import {Link} from 'react-router-dom';
import { Link } from 'react-router-dom';
import ContentHeader from '../components/ContentHeader';

export const SignupPage = () => {
    return (
        <>  
            <ContentHeader/>
            
            <h1>회원가입</h1>
            <div>
                <div>
                    <input type="text" placeholder="닉네임"/>
                <button>중복확인</button>
                </div>
                <div>
                    <input type="text" placeholder="이메일"/>
                    <button>중복확인</button>
                </div>
                <div>
                    <input type="password" placeholder="비밀번호" />
                </div>
                <div>
                    <input type="password" placeholder="비밀번호확인" />
                </div>

                <button>회원 가입</button>
                <Link to="/login">
                    <p>이미 계정이 있나요? 로그인하러 가기</p>
                </Link>
            </div>
        </>
    )
}

export default SignupPage;