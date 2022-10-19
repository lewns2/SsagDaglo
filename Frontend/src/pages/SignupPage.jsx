import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';

import Header from '../components/Header';
import ContentHeader from '../components/ContentHeader';
import * as useFetchUser from '../apis/FetchUser';

import "../style/UserForm.scss";


export const SignupPage = () => {

    const { register, handleSubmit, formState: { errors } } = useForm();

    const onSubmit = (data) => {
        console.log(data, errors);
        useFetchUser.reqSignup(data);
    }

    return (
        <>  
            <Header/>
            <ContentHeader/>

            <div className="UserForm">
            <div className="UserFormSubTitle">회원 정보 입력</div>

            <form className='UserFormWrapper' onSubmit={handleSubmit(onSubmit)}>
                <div className='UserInputWrapper'>
                    <label>닉네임</label>
                    <input className='UserInputText' id="userNickName" {...register("userNickName", {
                        required: { 
                            value: true, 
                            message: "닉네임을 입력해주세요" 
                        },
                        minLength: {
                            value: 2,
                            message: "닉네임은 최소 2자 이상으로 작성해주세요"
                        }
                        
                    })} placeholder="닉네임" />
                </div>
                {errors.userNickName && (<div className="error">{errors.userNickName.message}</div>)}
                
                <div className='UserInputWrapper'>
                    <label>이메일</label>
                    <input className='UserInputText' {...register("userEmail", {
                            required: { 
                                value: true, 
                                message: "이메일을 입력해주세요" 
                            },

                            pattern: {
                                value:/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i, 
                                message:"이메일 형식이 아닙니다" 
                            }
                        })} 
                        placeholder="이메일" />
                </div>
                {errors.userEmail && (<div className="error">{errors.userEmail.message}</div>)}
                
                <div className='UserInputWrapper'>
                    <label>비밀번호</label>
                    <input className='UserInputText' type='password' {...register("userPassword", {
                        required: { 
                            value: true, 
                            message: "비밀번호를 입력해주세요" 
                        },
                    })} placeholder="비밀번호" />
                </div>
                {errors.userPassword && (<div className="error">{errors.userPassword.message}</div>)}

                <div className='UserInputWrapper'>
                    <label>비밀번호 확인</label>
                    <input className='UserInputText' type='password' placeholder="비밀번호 확인" />
                </div>
                
                <button className='UserFormSubmitBtn'><p>가입하기</p></button>
            </form> 

            <Link to="/login">
                <p>이미 계정이 있나요? 로그인하러 가기</p>
            </Link>
            </div>
        </>
    )
}

export default SignupPage;