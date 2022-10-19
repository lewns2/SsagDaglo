import {Link} from 'react-router-dom';
import { useForm } from "react-hook-form";

import Header from '../components/Header';
import ContentHeader from '../components/ContentHeader';
import * as useFetchUser from '../apis/FetchUser';

import "../style/UserForm.scss";

export const LoginPage = () => {

    const { register, handleSubmit, formState: { errors }, } = useForm();

    const onSubmit = (data) => {
        console.log(data);
        useFetchUser.reqLogin(data);
    } 
    
    return (
        <>
            <Header/>
            <ContentHeader/>

            <div className="UserForm">
            <div className="UserFormSubTitle1">환영합니다!</div>

                <form className='UserFormWrapper' onSubmit={handleSubmit(onSubmit)}>
                    <div className='UserInputWrapper'>
                        <label>이메일</label>
                        <input className='UserInputText' id="email" type="text" placeholder="ssagdaglo@naver.com" 
                            {...register("userEmail")}
                        />
                    </div>
                    {errors.email && <p>{errors.email.message}</p>}
                    <div className='UserInputWrapper'>
                        <label>비밀번호</label>
                        <input className='UserInputText' id="password" type="password" placeholder="8자리 이상 입력해주세요" 
                            {...register("userPassword")}
                        />
                    </div>
                    
                    <p>아이디 찾기</p>
                    <p>비밀번호 찾기</p>
                    
                    <button type="submit" className='UserFormSubmitBtn'>로그인</button>
                </form>
                
            </div>
            
            <div>
                <Link to="/signup" className='UserFormSubmitBtn'>
                    회원가입
                </Link>
            </div>
            
        </>
    )
}

export default LoginPage;