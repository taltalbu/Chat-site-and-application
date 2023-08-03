import React, { useRef, useEffect, useContext, useState, createContext } from 'react'
import { useAppContext } from "./chatProvider.js";
import io from 'socket.io-client'
const WebsocketContext = createContext();
function SocketProvider({ children }) {
    const [isReady, setIsReady] = useState(false);
    const [newMessage, setNewMessage] = useState(null);
    const [newContact, setNewContact] = useState(null);
    const [deletingContact, setDeletingContact] = useState(null);
    const ws = useRef(null);
    const { user } = useAppContext();
    useEffect(() => {
        if(user && !isReady) {
            console.log("1");
            const socket = io("http://localhost:5000");
            const username = localStorage.getItem("username");
            console.log(username);
            socket.emit("new-user-connection", username)
            setIsReady(true)
            socket.on("dissconnect", () => setIsReady(false));
            socket.on("msg-recive", (event) => setNewMessage(event));
            socket.on("add-as-contact", (event) => setNewContact(event));
            socket.on("remove-contact",(event) => setDeletingContact(event))
            ws.current = socket;
        }
    }, [user])

    return (
        <WebsocketContext.Provider
            value={{
                newMessage,
                setNewMessage,
                setNewContact,
                deletingContact,
                setDeletingContact,
                newContact,
                isReady,
                ws,
            }}
        >
            {isReady && children}
        </WebsocketContext.Provider>
    );
};
const useSocketContext = () => {
    return useContext(WebsocketContext);
};

export { SocketProvider, useSocketContext };