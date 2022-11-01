import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';

import Header from '../components/Header';
import ContentHeader from '../components/ContentHeader';
import * as useFetchUser from '../apis/FetchUser';
import Alert from '../components/Alert';

import '../style/UserForm.scss';

export const LoginPage = () => {
  const navigate = useNavigate();

  const [isSuccessLogin, setIsSuccessLogin] = useState('false');
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  // [기능 1] 로그인 양식 제출
  const onSubmit = (data) => {
    let response = useFetchUser.reqLogin(data);
    response.then((res) => {
      console.log(`${res}`);
      {
        setIsSuccessLogin(`${res}`);
      }
      `${res}` === 'false' ? Alert(false, '실패') : Alert(true, '성공');
    });
  };

  // [기능 2] 로그인 완료 시, 파일 리스트 목록으로 이동
  useEffect(() => {
    if (isSuccessLogin !== 'false') {
      navigate('/list');
    }
  }, [isSuccessLogin]);

  return (
    <>
      <Header />
      <ContentHeader />

      <div className="UserForm">
        <div className="UserFormSubTitle1">환영합니다!</div>

        <form className="UserFormWrapper" onSubmit={handleSubmit(onSubmit)}>
          <div className="UserInputWrapper">
            <label>이메일</label>
            <input
              className="UserInputText"
              id="email"
              type="text"
              placeholder="ssagdaglo@naver.com"
              {...register('userEmail')}
            />
          </div>
          {errors.email && <p>{errors.email.message}</p>}
          <div className="UserInputWrapper">
            <label>비밀번호</label>
            <input
              className="UserInputText"
              id="password"
              type="password"
              placeholder="8자리 이상 입력해주세요"
              {...register('userPassword')}
            />
          </div>
          <div className="FindUserInfo">
            <p className="underline-hover-btn" style={{ cursor: 'pointer' }}>
              아이디 찾기
            </p>
            <p className="underline-hover-btn" style={{ cursor: 'pointer' }}>
              비밀번호 찾기
            </p>
          </div>

          <button type="submit" className="UserFormSubmitBtn">
            <p id="innerfont">로그인</p>
          </button>
        </form>
      </div>
      <p
        onClick={() => navigate('/signup')}
        style={{ cursor: 'pointer', textDecoration: 'underline' }}>
        처음이신가요? 회원가입 하러가기
      </p>
      <p style={{ color: 'gray' }}>싹다글로 서비스 이용을 위해, 별도 회원가입이 필요합니다.</p>
    </>
  );
};

export default LoginPage;
