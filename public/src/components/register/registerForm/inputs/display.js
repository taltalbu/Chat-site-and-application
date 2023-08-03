import React, { useEffect, useState } from 'react'
import SuccessImg from '../../icons/check.png'
import WrongImg from '../../icons/cross.png'
import Tooltip from './ToolTip/toolTip'
function Display({ handleChange, setDisplayOK, displayNameElement }) {
    const [CheckIcon, setCheckIcon] = useState('');


    function checkDisplaynameInput(displayNameElement) {
        const regex = /^[a-zA-Z ]{3,20}$/;
        return regex.test(displayNameElement);
    }
    // function checkDisplaynameValidation(e) {
    //     handleChange(e);
    //     if (e.target.value === '') {
    //         setCheckIcon('');
    //     }
    //     else if (checkDisplaynameInput(e.target.value)) {
    //         setDisplayOK(true);  
    //         setCheckIcon(SuccessImg);
    //     } else {
    //         setDisplayOK(false);
    //         setCheckIcon(WrongImg);
    //     }
    // }
    useEffect(()=> {
        if (displayNameElement === '') {
            setCheckIcon('');
        }
        else if (checkDisplaynameInput(displayNameElement)) {
            setDisplayOK(true);  
            setCheckIcon(SuccessImg);
        } else {
            setDisplayOK(false);
            setCheckIcon(WrongImg);
        }
    },[displayNameElement])

    function disableEnter(e) {
        if (e.keyCode === 13) {
            e.preventDefault();
            return false;
        }
    }

    return (
        <div className="input-box-registerScreen">
            <span className="details-registerScreen">Display Name</span>
            <Tooltip content="between 3-20 character, only letter" direction="bottom">
                <input type="text"
                    id="displayNameRegisterScreen"
                    placeholder="Enter display name"
                    name='displayName'
                    value={displayNameElement}
                    onChange={handleChange}
                    title=""
                    autoComplete="off"
                    onKeyDown={disableEnter}
                    required />
                    {CheckIcon ? <img src={CheckIcon} className="errorMessage-UsernameRegisterScreen" alt="" ></img> : null}
            </Tooltip>
        </div>
    )
}

export default Display