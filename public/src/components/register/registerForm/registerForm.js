import React, { useState } from 'react'
import '../register.css'
import { Link, useNavigate } from 'react-router-dom';
import UsernameInput from './inputs/username';
import Password from './inputs/password';
import ProfileUpload from './inputs/profileUpload';
import Display from './inputs/display';
function RegisterForm() {
    const [passwordOk, setIsPasswordOk] = useState(false);
    const [displayOK, setDisplayOK] = useState(false);
    const navigate = useNavigate();

    const [newUser, setNewUser] = useState({
        username: '',
        password: '',
        confirmPassword: '',
        displayName: '',
        profilePic: '',
        setNewUser: (username, password, displayName, profilePic) => {
            setNewUser({ ...newUser, username, password, displayName, profilePic });
        }
    });
    function clearNewUser() {
        setNewUser({ username: '', password: '', confirmPassword: '', displayName: '', profilePic: '' })

    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (displayOK && passwordOk) {
            const { username, displayName, password, profilePic } = newUser;

            try {
                let response = await fetch('http://localhost:5000/api/Users', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ username, displayName, password, profilePic }),
                });

                if (!response) {
                    alert("failed to regestration");
                    clearNewUser();
                    return;
                }
                if (response.status === 409) {
                    alert('User already exists');
                    clearNewUser();
                    return;
                }
                if (response.status === 200) {
                    navigate('/');
                    return;
                }

            } catch (e) {
                // failed
                console.error(e);
                alert(e);
            }
        } else {
            alert("details not match the requirment");
        }
    }

    function checkIsUserValid(e) {
        // const isAlreadyExsit = usersDataBase.find((user) => user.username === e.target.value);
        // if (isAlreadyExsit) {
        //     setIsUserExsit(true);

        //     return false;

        // } else {
        //     handleChange(e);
        //     setIsUserExsit(false);
        //     return true;
        // }
        handleChange(e);
        return true;
    }

    const handleChange = ((event) => {
        const name = event.target.name;
        const value = event.target.value;
        setNewUser({
            ...newUser,
            [name]: value
        });
    });

    return (
        <form id="registerForm-registerScreen" onSubmit={handleSubmit}>
            <div className="user-details-registerScreen">
                <UsernameInput checkIsUserValid={checkIsUserValid} usernameElement={newUser.username} />
                <Display handleChange={handleChange} setDisplayOK={setDisplayOK} displayNameElement={newUser.displayName} />
                <Password handleChange={handleChange} setIsPasswordOk={setIsPasswordOk}
                    passwordElement={newUser.password} confirmPasswordElement={newUser.confirmPassword} />
                <ProfileUpload handleChange={handleChange} profilePic={newUser.profilePic} />
                <div className="input-box-registerScreen" id="existing-account-registerScreen">
                    <Link to="/">To Log In to
                        an existing account</Link>
                </div>
            </div>
            <div className="button-registerScreen">
                <input type="submit" value="Register" />
            </div>
        </form>
    )
}

export default RegisterForm