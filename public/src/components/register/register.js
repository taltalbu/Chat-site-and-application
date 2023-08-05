import React, { useEffect } from 'react'
import './register.css'
import RegisterForm from './registerForm/registerForm'
import { useNavigate } from 'react-router-dom'
function RegisterPage() {

    return (
        <div className="container-registerScreen">
            <div className="container-card-registerScreen">
                <div className="header-registerScreen">
                    <div className="title-registerScreen">Registration</div>
                    <div className="logo-registerScreen">
                        <img src="Photos/ChatYTYPic.png" alt="" />
                    </div>
                </div>
                <div className="content-registerScreen">
                    <RegisterForm />
                    <div className="github-link-registerScreen">
                        <a href="https://github.com/taltalbu/Chat-site-and-application#readme" target="_blank" rel="noopener noreferrer">to know more about ChatYTY</a>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RegisterPage
