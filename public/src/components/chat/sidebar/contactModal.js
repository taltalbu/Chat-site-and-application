import React from 'react'
import { useAppContext } from '../context/chatProvider';
import { useSocketContext } from '../context/socketProvider';
function ContactModal({ setShowModal, token, currentChat, fetchContactList }) {
    const { ws } = useSocketContext();
    const { user, setSelectedPerson, selectedPerson } = useAppContext();
    const handleModalClose = (e) => {
        e.preventDefault();
        setShowModal(false);
    };

    const handleModalConfirm = () => {
        const deleteContact = async () => {
            try {
                const response = await fetch(
                    `http://localhost:5000/api/Chats/${currentChat.id}`,
                    {
                        method: "DELETE",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );

                if (response.status === 200) {
                    // Contact deletion successful
                    if (currentChat.id === selectedPerson?.id) {
                        setSelectedPerson(null);
                    }
                    // Emit socket event to remove contact from the other user's side
                    ws.current.emit("remove-contact", {
                        userDeleteing: user.usename,
                        userDeleted: currentChat.user.username,
                        chatDeleted: currentChat.id
                    });
                    // Fetch updated data after deletion
                    fetchContactList();
                } else {
                    // Other error occurred
                    console.log("Error deleting contact:", response.status);
                }
            } catch (error) {
                console.error("Error deleting contact:", error);
            }
        };

        deleteContact();
        setShowModal(false);
    };


    return (
        (
            <div className="modal">
                <div className="modal-content">
                    <h2>Contact detils</h2>
                    <div className="profile-info">
                        <div>
                            <p>contact username is: {currentChat.user.username}</p>
                            <p>contact display name is: {currentChat.user.displayName}</p>
                        </div>
                        <div className="chat-profile-pic">
                            <img src={currentChat.user.profilePic} alt="" />
                        </div>
                    </div>
                    <div className="modal-actions">
                        <button className="cancel-button" onClick={handleModalClose}>
                            OK
                        </button>
                        <button className="delete-button" onClick={handleModalConfirm}>
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        )
    )
}

export default ContactModal;