import React, { useState, useEffect, useRef } from 'react';
import '../chat.css';
import Message from './message';
import WriteMessage from './writeMessage';
import ChatHeader from './chatHeader/chatHeader';
import { useAppContext } from "../context/chatProvider";
import { useSocketContext } from '../context/socketProvider';
function Chatcontainer({ token, fetchNewToken, isLoaded, setIsLoaded }) {
    const inputRef = useRef(null);
    const { sortedContactList,setsidebar, user, selectedPerson } = useAppContext();
    const [messages, setMessages] = useState([]);
  const {ws,setNewMessage} = useSocketContext();
  const fetchData = async () => {
    if(selectedPerson ) {
      try {
          let response = await fetch(`http://localhost:5000/api/Chats/${selectedPerson.id}/Messages`, {
              headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${token}`,
              },
          });
          if (response.status !== 200) {
              await fetchNewToken();
              response = await fetch(`http://localhost:5000/api/Chats/${selectedPerson.id}/Messages`, {
                  headers: {
                      'Content-Type': 'application/json',
                      'Authorization': `Bearer ${token}`,
                  },
              });
          }
          const messagesData = await response.json();
          setMessagesFormat(messagesData);
      } catch (error) {
          console.error(error);
      }
  };
}
    useEffect(() => {
        setMessages([])
        setIsLoaded(true);
        setNewMessage(null)
  
        fetchData();
    }, [selectedPerson]);

    function addMsgFormat (msg) {
        return (
          <div key={msg.id}>
            {selectedPerson.user.username !== msg.sender.username ? (
              <div className="message-send">
                <img src={msg.sender.profilePic} alt="" />
                <p className="chat-message">
                  {msg.content}
                  <br />
                  <span className="time-message">
                    {new Date(msg.created).toLocaleTimeString()} {new Date(msg.created).toLocaleDateString()}
                  </span>
                </p>
              </div>
            ) : (
              <div className="message-get">
                <img src={selectedPerson.user.profilePic} alt="" />
                <p className="chat-message">
                  {msg.content}
                  <br />
                  <span className="time-message">
                    {new Date(msg.created).toLocaleTimeString()} {new Date(msg.created).toLocaleDateString()}
                  </span>
                </p>
              </div>
            )}
          </div>
        );
      }


    async function addMessage(msg) {
        const newMsg = { msg: msg };
        let response = await fetch(`http://localhost:5000/api/Chats/${selectedPerson.id}/Messages`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify(newMsg),
        });
        if (response.status === 401) {
            await fetchNewToken();
            response = await fetch(`http://localhost:5000/api/Chats/${selectedPerson.id}/Messages`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
                body: JSON.stringify(newMsg),
            });
        }
        const newMsgJson = await response.json();
        ws.current.emit("send-msg", {
            to: selectedPerson.user.username,
            from: user.username,
            msg: newMsgJson,
            chatID:selectedPerson.id
        })
        const newMsgFormat = addMsgFormat(newMsgJson)
        setMessages((prevMessages) => [...prevMessages,newMsgFormat]);
        selectedPerson.lastMessage = newMsgJson
        moveSelectedPersonToTop(selectedPerson)
    }

    

    const moveSelectedPersonToTop = (chatToMove) => {
        const newArray = [...sortedContactList]; // Create a copy of the array
        const index = newArray.findIndex((obj) => obj.id === chatToMove.id); // Find the index of the object
        if (index >= 0) {
            newArray.splice(index, 1); // Remove the object from its current position
        }
        newArray.unshift(chatToMove); // Add the object at the beginning of the array
        setsidebar(newArray); // Update the state with the new array
    };


    function setMessagesFormat(messages) {
        const reversedMessageList = messages.slice(0).reverse().map((side) => {
          return (
            <div key={side.id}>
              {selectedPerson.user.username !== side.sender.username ? (
                <div className="message-send">
                  <img src={side.sender.profilePic} alt="" />
                  <p className="chat-message">
                    {side.content}
                    <br />
                    <span className="time-message">
                      {new Date(side.created).toLocaleTimeString()} {new Date(side.created).toLocaleDateString()}
                    </span>
                  </p>
                </div>
              ) : (
                <div className="message-get">
                  <img src={selectedPerson.user.profilePic} alt="" />
                  <p className="chat-message">
                    {side.content}
                    <br />
                    <span className="time-message">
                      {new Date(side.created).toLocaleTimeString()} {new Date(side.created).toLocaleDateString()}
                    </span>
                  </p>
                </div>
              )}
            </div>
          );
        });
    
        setMessages(reversedMessageList);
      }

    return (
        <div className="chat-container">
            <ChatHeader
                selectedPerson={selectedPerson}
            />
            <div className="message-container">
                {isLoaded && <Message
                    messages={messages}
                    setMessages={setMessages}
                    inputRef={inputRef}
                    selectedPerson={selectedPerson}
                    addMsgFormat={addMsgFormat}
                />}
            </div>
            <WriteMessage
                addMessage={addMessage}
                inputRef={inputRef} />
        </div>
    );
}

export default Chatcontainer;