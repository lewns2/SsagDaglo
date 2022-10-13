// import { useState, useEffect } from 'react';
import {Link} from 'react-router-dom';

import Header from '../components/Header';

export const StartPage = () => {
    return (
        <>  
            <Header/>
            
            <h1>시작페이지</h1>

            <div>
                <div>
                    음성을 다 글로 쉽고 빠르게
                </div>
                <div>
                    회의록, 대화록, 강의 노트, 영상 자막까지 이제 모든 타이핑은 싹다글로에 맡기고, 더욱 중요한 업무에 집중하세요
                </div>
            </div>

            <div>
                <Link to="/list">
                    <button>
                    시작하기
                    </button>
                </Link>
            </div>
            
        </>
    )
}

export default StartPage;