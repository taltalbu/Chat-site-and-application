import React, { useState } from 'react'
import Logout from './logout';
function ChatHeader({ selectedPerson }) {
    const [showModal, setShowModal] = useState(false);
    const handlePhotoClick = () => {
        setShowModal(true);
    };
    return (
        <div className="header-contact">
            <div className="profile-pic">
                <img src={selectedPerson?.user.profilePic} alt="" onClick={handlePhotoClick} />
            </div>

            {showModal && (
                <div className="modal" onClick={() => setShowModal(false)}>
                    <img className="modal-image" src={selectedPerson?.user.profilePic} alt="" />
                </div>
            )}

            <div className="chat-header-content">
                {selectedPerson?.user.displayName}
            </div>
            <Logout/>
        </div>
    )
}

export default ChatHeader