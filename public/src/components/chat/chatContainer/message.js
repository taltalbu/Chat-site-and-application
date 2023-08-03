import React, { useEffect, useRef } from 'react';
import '../chat.css';
import { useSocketContext } from '../context/socketProvider';
function Message({ messages, setMessages, inputRef, selectedPerson, addMsgFormat }) {
  const { newMessage } = useSocketContext();
  const lastMessageRef = useRef(null);

  useEffect(() => {
    if (newMessage) {
      if (newMessage.chatID === selectedPerson.id) {
          const newMsg = addMsgFormat(newMessage.msg)
          setMessages((prevMessages) => [...prevMessages, newMsg]);
        }
    }
  }, [newMessage]);

  useEffect(() => {
    if (lastMessageRef.current) {
      lastMessageRef.current.focus();
      inputRef.current.value = '';
      inputRef.current.focus();
    }
  }, [messages]);


  return (
    <>
      {messages}
      <div ref={lastMessageRef} tabIndex={-1}></div>
    </>
  );
}

export default Message;
