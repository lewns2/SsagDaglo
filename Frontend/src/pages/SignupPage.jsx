import { useState, useEffect } from 'react';
import { json, Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';

import Header from '../components/Header';
import ContentHeader from '../components/ContentHeader';
import * as useFetchUser from '../apis/FetchUser';
import Alert from '../components/Alert';

import "../style/UserForm.scss";


export const SignupPage = () => {

    const navigate = useNavigate();

    const { register, handleSubmit, formState: { errors }, getValues } = useForm();
    const [isSuccessSignUp, setIsSuccessSignUp] = useState();
    const [isCheckedNickName, setIsCheckedNickName] = useState('false');
    const [isCheckedEmail, setIsCheckedEmail] = useState('false');

    // 회원 가입 양식 제출
    const onSubmit = (data) => {
        if(isCheckedNickName === 'false') {
            Alert(false, '닉네임 중복 확인을 해주세요.')
        }
        else if(isCheckedEmail === 'false') {
            Alert(false, '이메일 중복 확인을 해주세요')
        }
        else {
            let response = useFetchUser.reqSignup(data);
            response.then((res) => { 
                {setIsSuccessSignUp(`${res}`)}
                `${res}` === 'true' ? Alert(true, '회원 가입 성공'): Alert(false, '회원 가입 실패')
            })
        }
    }

    // 중복 확인 : 1. 닉네임, 2. 이메일
    const handleDoubleCheck = (type) => {
        const values = getValues();
        
        if(type === "nickName") {
            if(values.userNickName === "") {
                Alert(false, '닉네임을 입력해주세요');
            }
            else {
                let res = useFetchUser.reqCheckNickName(JSON.stringify({userNickName : values.userNickName}));
                res.then((res) => {
                    setIsCheckedNickName(`${res}`);
                    `${res}` === 'true' ? Alert(true, '사용 가능한 닉네임입니다.') : Alert(false, '중복된 닉네임이 존재합니다.')
                })
            }
        }
        if(type === 'email') {
            if(values.userEmail === "") {
                Alert(false, '이메일을 입력해주세요')
            }
            else {
                let res = useFetchUser.reqCheckEmail(JSON.stringify({userEmail : values.userEmail}));
                res.then((res) => {
                    setIsCheckedEmail(`${res}`);
                    `${res}` === 'true' ? Alert(true, '사용 가능한 이메일입니다.'): Alert(false, '중복된 이메일이 존재합니다.')
                })
            }
        }
    }

    useEffect(() => {
        if(isSuccessSignUp) {
            navigate('/login');
        }
    }, [isSuccessSignUp]);

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
                            value: 3,
                            message: "닉네임은 최소 3자 이상으로 작성해주세요"
                        }
                        
                    })} placeholder="닉네임" />
                    <button type="button" onClick={() => handleDoubleCheck('nickName')}>중복 확인</button>
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
                    <button type="button" onClick={() => handleDoubleCheck('email')}>중복 확인</button>
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