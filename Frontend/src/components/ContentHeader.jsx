import { useNavigate } from 'react-router-dom';

import "./ContentHeader.scss";

export const ContentHeader = () => {

    let navigate = useNavigate();

    return (
        <div className='cotentheader'>
            <button className='prevBtn' onClick={() => {navigate(-1)}}>
                뒤로가기
            </button>
        </div>
    )
}

export default ContentHeader;