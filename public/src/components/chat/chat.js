import React, { useState, useEffect } from 'react';
import './chat.css';
import ChatContainer from './chatContainer/chatContainer.js';
import { useNavigate } from 'react-router-dom';
import Logout from './chatContainer/chatHeader/logout.js';
import Sidebar from './sidebar/sidebar.js';
import { useAppContext } from "./context/chatProvider.js";
import { SocketProvider } from './context/socketProvider';
import CustomToast from '../UI/toastify/toastify.js'
function Chat() {

  const userLogin = {
    username: localStorage.getItem("username"),
    password: localStorage.getItem("password")
  };
  const { user, setUser, selectedPerson } = useAppContext();
  const [isLoaded, setIsLoaded] = useState(false);
  const [token, SetToken] = useState(localStorage.getItem("token"));
  const [toastMessage,setToastMessage] = useState();
  const navigate = useNavigate();
  const getUser = async () => {
    let response = await fetch(`http://localhost:5000/api/Users/${userLogin.username}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`,
      },
    });
    const tUser = await response.json();

    setUser(tUser);
  }

  useEffect(() => {
    const fecthData = async () => {
      if (userLogin.username && userLogin.password && token) {
        if (!user) {
          await getUser(); // Await the getUser function
        }
      } else {
        navigate('/');
      }
    }
    fecthData();
  }, []);





  async function fetchNewToken() {
    delete localStorage.token;
    let response = await fetch('http://localhost:5000/api/Tokens', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userLogin),
    });

    if (response.status === 200) {
      let newToken = await response.text();
      localStorage.setItem("token", token);
      SetToken(newToken);
      return;
    }
  }
  return (
    <div className="container-chatScreen">
      <div className="card-chatScreen">
      {toastMessage && (
      <CustomToast message={toastMessage} onClose={() => setToastMessage('')} />
    )}
        <SocketProvider>
           <Sidebar
            token={token}
            fetchNewToken={fetchNewToken}
            setIsLoaded={setIsLoaded}
            setToastMessage={setToastMessage}
          />
          {selectedPerson ?
            (
              <ChatContainer
                token={token}
                fetchNewToken={fetchNewToken}
                isLoaded={isLoaded}
                setIsLoaded={setIsLoaded}
              />
            )
            :
            (<div id="startScreenChat" className="chat-container">
              <div className="logout-start-screen">
                <Logout/>
              </div>
              <img src="Photos/slogenLoginScreen.png" alt="" />
            </div>)
          }
        </SocketProvider>

      </div>
    </div>
  )
}
export default Chat;