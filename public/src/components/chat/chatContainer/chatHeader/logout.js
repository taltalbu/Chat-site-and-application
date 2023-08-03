import React from 'react';
import { Link,useNavigate } from 'react-router-dom';
import '../../chat.css';
import { useAppContext } from "../../context/chatProvider.js";
import { useSocketContext } from '../../context/socketProvider';
function Logout() {
  const {ws} = useSocketContext();
  const {user} = useAppContext();
  const navigate = useNavigate();
  function handleLogOut() {
    navigate('/');
    localStorage.clear();
    ws.current.emit("dissconnect",user.username);    
  };

    return (
        <div className="logout-pic">
        <Link to="/" onClick={handleLogOut}>
          <img src="Photos/logout.png" alt="" />
        </Link>
      </div>
    );
}

export default Logout;