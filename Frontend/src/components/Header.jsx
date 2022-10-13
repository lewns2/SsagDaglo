import logoImage from "../assets/logo.png"
import {Link} from 'react-router-dom';

export const Header = () => {
    return (
        <>
        <div>
            <img src={logoImage} alt="로고"/>

            <Link to="login">
                로그인
            </Link>
            <Link to="signup">
                회원가입
            </Link>
        </div>    
        </>
    )
}

export default Header;