import React, { useState, useEffect } from 'react'
import Searchside from '../search/searchside.js'
import ChatResult from './sidebarResult.js';
import AddContact from '../addContact/addContact.js';
import { useAppContext } from "../context/chatProvider.js";
import { useSocketContext } from '../context/socketProvider.js';
function Sidebar({token, fetchNewToken, setIsLoaded,setToastMessage }) {
  const {selectedPerson,user,setSelectedPerson, sortedContactList, setsidebar, setSearchContact } = useAppContext();
  const {newMessage,deletingContact,setDeletingContact} = useSocketContext();
  async function fetchContactList() {
    let response = await fetch(`http://localhost:5000/api/Chats`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    const tContactList = await response.json();
    setsidebar(tContactList)
  }
  const getContactFromServer = ()=>{
    fetchContactList();

  }

  useEffect(() => {
    fetchContactList();
  }, [user,selectedPerson])


  const doSearchSidebar = function (q) {
    const filtered = sortedContactList.filter((chat) => chat.user.displayName.includes(q));
    setSearchContact(filtered);
  };

  const handleSidebarItemClick = (person) => {
    setIsLoaded(false);
    setSelectedPerson(person); // Update the selected person's object
  };


  useEffect(() => {
    if (newMessage) {
      const newArray = [...sortedContactList]; // Create a copy of the array
      const messageRecivePerson = newArray.find((chat) => newMessage.chatID === chat.id);
      messageRecivePerson.lastMessage = newMessage.msg;
      const index = newArray.findIndex((obj) => obj.id === messageRecivePerson.id); // Find the index of the object
      if (index >= 0) {
        newArray.splice(index, 1); // Remove the object from its current position
      }
      newArray.unshift(messageRecivePerson); // Add the object at the beginning of the array
      setsidebar(newArray); // Update the state with the new array
      setToastMessage(`New message from ${newMessage.from}: ${newMessage.msg.content}`);
    }
  }, [newMessage]);


  useEffect(()=>{
    if(deletingContact) {
      if(selectedPerson) {
        if(selectedPerson.id === deletingContact) {
          setSelectedPerson(null);
        }
      }
      fetchContactList();
      setDeletingContact(null);
    }
  },[deletingContact])



  return (
    <div className="sidebar">
      <div className="header-sidebar">
        <div className="logo-chatScreen">
          <div className="profile-pic">
            <img src={user?.profilePic} alt=""></img>
          </div>
          <div className="header-logo-chatScreen">{user?.displayName}</div>
        </div>
        <div className="gudget"></div>
      </div>
      <div className="sidebar-search">
        <Searchside setSearchContact={doSearchSidebar} />
        <AddContact 
          token={token}
          fetchNewToken={fetchNewToken}
          setIsLoaded={setIsLoaded}
          getContactFromServer={getContactFromServer}
        />
      </div>
      <ChatResult 
        onSidebarItemClick={handleSidebarItemClick}
        token={token}
        fetchContactList={fetchContactList}
        />
    </div>
  )
}

export default Sidebar