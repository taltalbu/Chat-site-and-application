import React from 'react';
import './login.css';
import LoginForm from './loginForm/loginForm';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
function Login({onLoginSeccuss}) {

    return (
        <div className="container-loginScreen">
            <div className="card-loginScreen">
                <div className="wrapper-loginScreen">
                    <div className="title-loginScreen">Login To ChatYTY</div>
                    <LoginForm onLoginSeccuss={onLoginSeccuss}/>
                </div>
            </div>
            <div id="SloganID-loginScreen" className="slogan-loginScreen">
                <img src="Photos/slogenLoginScreen.png" className="slogan-loginScreen" alt=""></img>
            </div>
        </div>
    )
}

export default Login