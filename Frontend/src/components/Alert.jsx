import Swal from "sweetalert2"

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
            // footer: '<a href="">Why do I have this issue?</a>'
        })
    }
}

export default Alert;