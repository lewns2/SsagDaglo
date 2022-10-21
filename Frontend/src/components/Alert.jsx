import Swal from "sweetalert2"

// 프로젝트에 사용되는 알림창 컴포넌트
export const Alert = (isTrue, message) => {
    if(isTrue === true) {
        Swal.fire({
            icon: 'success',
            title: `${message}`,
            showConfirmButton: false,
            timer: 1500
        })
    }
    else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: `${message}`,
        })
    }
}

export default Alert;