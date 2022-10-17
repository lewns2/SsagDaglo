import logoImage from "../assets/logo.png"
import {Link} from 'react-router-dom';

import "./Header.scss";

export const Header = () => {
    return (
        <div className="header">
            <Link to="/">
                <img src={logoImage} alt="로고"/>
            </Link>
            <div>
                <Link to="/login">
                    로그인
                </Link>
                <Link to="/signup">
                    회원가입
                </Link>
            </div>
        </div>    
    )
}

export default Header;