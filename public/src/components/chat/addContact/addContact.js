import React, { useRef, useState,useEffect } from 'react';
import '../chat.css';
import { useAppContext } from "../context/chatProvider.js";
import { useSocketContext } from '../context/socketProvider.js';
function AddContact({ getContactFromServer,token, fetchNewToken, setIsLoaded, socket }) {
 const {user, setSelectedPerson ,setsidebar,sortedContactList} = useAppContext();
 const {ws,newContact,setNewContact} = useSocketContext();
  const useRefUser = useRef();
  const [modalOpen, setModalOpen] = useState(false);

  function checkUsernameInput(username) {
    const regex = /^[a-zA-Z][a-zA-Z0-9]* ?[a-zA-Z0-9]*$/;
    return regex.test(username);
  }

  function addGivenContact(newContact) {
    setIsLoaded(false);
    const newArray = [...sortedContactList]; // Create a copy of the array
    
    newArray.unshift(newContact); // Add the object at the beginning of the array
    setsidebar(newArray); // Update the state with the new array
    setSelectedPerson(newContact);
  }



  useEffect(() => {
    if(newContact) {
    getContactFromServer();
      setNewContact(false);
    }
  }, [newContact]);



  const addNewContactFetch = async (username) => {
    debugger;
    let response = await fetch(`http://localhost:5000/api/Chats`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ username: username }),
    });
    if (response.status === 401) {
      console.log("second attempt", response);
      await fetchNewToken();
      let response = await fetch(`http://localhost:5000/api/Chats`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify({ username: username }),
      });
      if (response.status === 200) {
        const newContact = await response.json();
        console.log(newContact);
        const newChat = {id:newContact.id,user:user,messages:[]}
        ws.current.emit("add-contact", {
          to: user.username,
          from: username,
          chat: newChat
        });
        addGivenContact(newContact);
      }
      if (response.status === 400 || response.status === 404) {
        alert("there is no such user");
      }
    }
    if (response.status === 400) {
      alert("there is no such user");
    }
    if (response.status === 200) {
      const newContact = await response.json();
      const newChat = {id:newContact.id,user:user,messages:[]}
      ws.current.emit("add-contact", {
        to: newContact.user.username,
        from: username,
        chat: newChat
      });
      addGivenContact(newContact);
      setModalOpen(false);
      return;
    }
  }


  const thisIsMe = (e)=> {
    return user.username === e;
  }

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    const username = useRefUser.current.value
    if (checkUsernameInput(username) && !thisIsMe(username)) {
      await addNewContactFetch(username);
      useRefUser.current.value = ''
    } else {
      alert("username doest match terms");
    }
  }

  return (
    <div className="addContact-container">
      <label htmlFor="modal-checkbox" className="button-contact">
        <img src="./Photos/addContact-icon.png" alt="Open Modal" />
      </label>
      <input type="checkbox" id="modal-checkbox" className="modal-checkbox" checked={modalOpen} onChange={() => setModalOpen(!modalOpen)} />
      <div className={`modal-AddContact ${modalOpen ? 'open' : ''}`}>
        <div className="modal-content-forAddContact">
          <label htmlFor="modal-checkbox" className="close" onClick={() => setModalOpen(true)}>&times;</label>
          <form id="addContact-form" onSubmit={handleFormSubmit} >
            <h2>Add Contact</h2>
            <input type="text"
              name="inputName"
              id="username"
              placeholder="Enter username"
              ref={useRefUser}
              autoComplete="off"
              title="3-20 characters, letters and numbers only"
              required />
            <label className="condition-addContact-modal">please enter a username you want to add</label>
            <button type="submit" id="addBtn">+</button>
          </form>
        </div>
      </div>

    </div>
  );
}

export default AddContact;