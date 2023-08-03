import React from 'react';
import SidebarChat from './sidebarChat';
import { useAppContext } from "../context/chatProvider";
function ChatResult({ onSidebarItemClick,token,fetchContactList }) {
  const { selectedPerson, searchContact } = useAppContext()
  const handleSidebarItemClick = (e, person) => {
    console.log(e.button);
    if (e && e.button === 0) { // Check if the button clicked is the right mouse button (button code 2)
      // e.preventDefault(); // Prevent the default action of the right-click event
      console.log("sadsad");
      if (person) {
        if (person === selectedPerson) {
          return;
        }
        onSidebarItemClick(person);
      }
    }
  };
  let sidebarList = null;
    sidebarList = searchContact?.map((chat) => (
      <SidebarChat
        currentChat={chat}
        token={token}
        fetchContactList={fetchContactList}
        onClick={(e) => {
          handleSidebarItemClick(e,chat)
        }} 
      />
    ));
  return <div className="sidebar-chats">{sidebarList}</div>;

}

export default ChatResult;