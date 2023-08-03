import React, { useEffect, useState } from 'react'
import '../../register.css'
import SuccessImg from '../../icons/check.png'
import WrongImg from '../../icons/cross.png'
import Tooltip from './ToolTip/toolTip'
function Username({ checkIsUserValid, usernameElement }) {
    // const [CheckIcon, setCheckIcon] = useState('');


    // function checkUsernameInput(username) {
    //     const regex = /^[a-zA-Z0-9]{3,20}$/;
    //     return regex.test(username);
    // }
    // function checkUsernameValidation(e) {
    //     if (e.target.value === '') {
    //         setCheckIcon('');
    //     }
    //     else if (checkUsernameInput(e.target.value)) {
    //         if (checkIsUserValid(e)) {
    //             setCheckIcon(SuccessImg);
    //         } else {
    //             setCheckIcon(WrongImg);
    //         }
    //     } else {

    //         setCheckIcon(WrongImg);
    //     }
    // }

    // useEffect(()=>{
    //     if (usernameElement === '') {
    //         setCheckIcon('');
    //     }
    //     else if (checkUsernameInput(usernameElement)) {
    //         if (checkIsUserValid(usernameElement)) {
    //             setCheckIcon(SuccessImg);
    //         } else {
    //             setCheckIcon(WrongImg);
    //         }
    //     } else {

    //         setCheckIcon(WrongImg);
    //     }
    // },[usernameElement])


    
    function disableEnter(e) {
        if (e.keyCode === 13) {
            e.preventDefault();
            return false;
        }
    }

    return (
        <div className="input-box-registerScreen">
            <span className="details-registerScreen">Username</span>
            <Tooltip content="between 3-20 character" direction="bottom">
                <input type="text"
                    id="usernameRegisterScreen"
                    placeholder="Enter your username"
                    name="username"
                    value={usernameElement}
                    onChange={checkIsUserValid}
                    title=""
                    autoComplete="off"
                    onKeyDown={disableEnter}
                    required />
            </Tooltip>
            {/* {CheckIcon ? <img src={CheckIcon} className="errorMessage-UsernameRegisterScreen" alt="" ></img> : null} */}
        </div>
    )
}

export default Username