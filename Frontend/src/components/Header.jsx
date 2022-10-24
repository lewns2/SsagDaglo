import logoImage from "../assets/logo.png"
import { useNavigate } from 'react-router-dom';

import "./Header.scss";
import { useEffect, useState } from "react";

export const Header = () => {
    const navigate = useNavigate();
    const [isLoin, setIsLogin] = useState(false);

    useEffect(() => {
        let userNickName = sessionStorage.getItem('userNickName');
        setIsLogin(userNickName);
    }, []);

    useEffect(() => {
    }, [isLoin]);
     
    // 로그아웃 시, 세션 데이터 삭제
    const handleLogout = () => {
        sessionStorage.clear();
        setIsLogin(false);
    }

    return (
        <div className="header">
            
            <img src={logoImage} onClick={() => navigate('/')} alt="로고" style={{cursor : 'pointer'}}/>
            
            { !isLoin ? (
                    <div  className="navMenu">
                        <p className="underline-hover-btn" onClick={() => navigate('/login')} style = {{cursor: "pointer"}} >로그인</p>
                        <p className="underline-hover-btn" onClick={() => navigate('/signup') } style = {{cursor: "pointer"}}>회원가입</p>
                    </div>
                ) : (
                    <div className="navMenu">
                        <p>{isLoin}님</p>
                        <p className="underline-hover-btn" onClick={handleLogout} style = {{cursor: "pointer"}}>로그아웃</p>
                    </div>
                )
            }
            
            
        </div>    
    )
}

export default Header;