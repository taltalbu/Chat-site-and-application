import '../login.css';
import { useRef, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function LoginForm() {
    const useRefUsername = useRef();
    const useRefPassword = useRef();
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const username = useRefUsername.current.value;
            const password = useRefPassword.current.value;
            const user = { username: username, password: password };
            handleReset();
            let response = await fetch('http://localhost:5000/api/Tokens', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            if (!response) {
                alert("failed to login");
                return;
            }
            if (response.status === 404) {
                alert("username or password incorrect");
                return;
            }
            if (response.status === 200) {
                let token = await response.text();
                localStorage.setItem("token", token);
                localStorage.setItem("username",username);
                localStorage.setItem("password",password);
                navigate('/chat');
                return;
            }
        } catch (e) {
            // failed
            console.error(e);
        }
    }

    const handleReset = () => {
        useRefUsername.current.value = ''
        useRefPassword.current.value = ''
    }

    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = (e) => {
        if (e.keyCode === 13) {
            return;
        }

        e.preventDefault();
        setShowPassword(!showPassword);
    };

    function disableEnter(e) {
        if (e.keyCode === 13) {
            handleSubmit(e);
            return false;
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="field">
                <input type="text"
                    id="usernameLoignScreen"
                    ref={useRefUsername}
                    onKeyDown={disableEnter}
                    required></input>
                <label>Username</label>
            </div>
            <div className="field">
                <input type={showPassword ? 'text' : 'password'}
                    id="passwordLoignScreen"
                    ref={useRefPassword}
                    onKeyDown={disableEnter}
                    required></input>
                <button className="show-password"
                    onClick={togglePasswordVisibility}
                >
                    <img
                        src={showPassword ? 'PHotos/showPassword.png' : 'Photos/hidePassword.png'}
                        alt=''
                    />
                </button>
                <label>Password</label>
            </div>
            <div className="signup-link">Want to know about ChatYTY?
                <a href="https://github.com/taltalbu/Chat-site-and-application#readme" target="_blank" rel="noopener noreferrer">Click here
                </a>
            </div>
            <div className="field">
                <input type="submit" value="Login"></input>
            </div>
            <div className="signup-link">Not a member? <Link to="/register" >Signup now</Link></div>
        </form>
    )
}

export default LoginForm

