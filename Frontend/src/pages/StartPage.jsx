import {Link} from 'react-router-dom';

import Header from '../components/Header';

import '../style/StartPage.scss'

export const StartPage = () => {
    return (
        <div className='container'>  
            <Header/>

            <div className="descript">
                <p id="maintitle">
                    음성을 다 글로 쉽고 빠르게
                </p>
                <p id="subtitle1">
                    회의록, 대화록, 강의 노트, 영상 자막까지 이제 모든 타이핑은 싹다글로에 맡기고,
                </p>
                <p id="subtitle2">
                    더욱 중요한 업무에 집중하세요
                </p>
            </div>

            <div className="servicestart">
                <Link to="/list">
                    <button id="startBtn">
                    시작하기
                    </button>
                </Link>
            </div>
            
        </div>
    )
}

export default StartPage;