import '../chat.css'
import React, { useState } from 'react';
import { useAppContext } from '../context/chatProvider';
import { useSocketContext } from '../context/socketProvider';
import ContactModal from './contactModal';


function Sidebar({ onClick, currentChat, token, fetchContactList }) {
  const { ws } = useSocketContext();
  const { user, setSelectedPerson, selectedPerson } = useAppContext();
  const [showModal, setShowModal] = useState(false);

  const handleContextMenu = (event) => {
    event.preventDefault();
    setShowModal(true);
  };




  const time = currentChat.lastMessage ? new Date(currentChat.lastMessage.created).toLocaleTimeString() : ''
  const date = currentChat.lastMessage ? new Date(currentChat.lastMessage.created).toLocaleDateString() : ''
  return (
    <>
      <div className="sidebar-chat"
        style={selectedPerson && { backgroundColor: selectedPerson?.id === currentChat.id ? '#f0eae6' : '' }}
        onClick={onClick}
        onContextMenu={handleContextMenu}>
        <div className="chat-profile-pic">
          <img src={currentChat.user.profilePic} alt="" />
        </div>
        <div className="chat-info">
          <div className="chat-info-header">
            <div className="chat-info-displayname">
              {currentChat.user.displayName}
            </div>
            <div className="time">
              {time} {date}
            </div>
          </div>
          <div>
            <p>{currentChat.lastMessage ? currentChat.lastMessage.content : ''}</p>
          </div>
        </div>
      </div>
      {showModal &&
        <ContactModal
          fetchContactList={fetchContactList}
          token={token}
          setShowModal={setShowModal}
          currentChat={currentChat}
        />}
    </>
  );
}

export default Sidebar;