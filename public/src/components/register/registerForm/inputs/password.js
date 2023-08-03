import React, { useEffect } from 'react'
import { useState } from 'react';
import SuccessImg from '../../icons/check.png'
import WrongImg from '../../icons/cross.png'
import Tooltip from './ToolTip/toolTip';
function Password({ handleChange, setIsPasswordOk,passwordElement,confirmPasswordElement }) {
    const [checkPasswordStr, setCheckPasswordStr] = useState('');
    const [CheckIconImg, setCheckIconImg] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const handlePasswordChange = (e) => {
        if(e.target.name === "password") {
        isPasswordMatchRegex(e.target.value)
            ? setCheckPasswordStr('password is ok')
            : setCheckPasswordStr('password doesnt match the terms');
        }
        handleChange(e);
    };
    function isPasswordMatchRegex(password) {
        const regex = /^(?=.*[A-Z])(?=.*[0-9]).{8,}$/;
        return regex.test(password);
    }
    useEffect(() => {
        if ((confirmPasswordElement !== '') && (passwordElement !== '')) {
            if (confirmPasswordElement === passwordElement && isPasswordMatchRegex(passwordElement)) {
                setIsPasswordOk(true);
                setCheckIconImg(SuccessImg);
            } else {
                setCheckIconImg(WrongImg);
                setIsPasswordOk(false);
            }
        } else {
            setCheckIconImg('')
        }
        // eslint-disable-next-line
    }, [confirmPasswordElement, passwordElement]);

    const togglePasswordVisibility = (e) => {
        if (e.keyCode === 13) {
            return;
        }

        e.preventDefault();
        setShowPassword(!showPassword);
    };

    function disableEnter(e) {
        if (e.keyCode === 13) {
            e.preventDefault();
            return false;
        }
    }


    return (
        <>
            <div className="input-box-registerScreen">
                <span className="details-registerScreen">Password</span>
                    <Tooltip content="8 character, 1 upper case, 1 number." direction="bottom">
                        <input type={showPassword ? "text" : "password"}
                            id="passwordRegisterScreen"
                            placeholder="Enter your password"
                            name='password'
                            value={passwordElement}
                            onChange={handlePasswordChange}
                            title=""
                            onKeyDown={disableEnter}
                            required />
                    <button className="show-password"
                        onClick={togglePasswordVisibility}
                    >
                        <img
                            src={showPassword ? 'PHotos/showPassword.png' : 'Photos/hidePassword.png'}
                            alt=''
                        />
                    </button>
                    </Tooltip>

                <span className="details-registerScreen">{checkPasswordStr}</span>
            </div>
            <div className="input-box-registerScreen">
                <span className="details-registerScreen">Confirm Password</span>
                <input type={showPassword ? "text" : "password"}
                    id="confirmPasswordRegisterScreen"
                    placeholder="Confirm your password"
                    name='confirmPassword'
                    value={confirmPasswordElement}
                    onChange={handlePasswordChange}
                    title=""
                    onKeyDown={disableEnter}
                    required />
                {CheckIconImg ? <img src={CheckIconImg} className="errorMessage-UsernameRegisterScreen" alt="" ></img> : null}
            </div>
        </>
    )
}

export default Password