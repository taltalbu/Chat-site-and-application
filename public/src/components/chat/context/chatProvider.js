import { createContext, useContext, useState } from "react";
const ChatContext = createContext();
const ChatProvider = ({ children }) => {
    const [selectedPerson, setSelectedPerson] = useState();
    const [user, setUser] = useState();
    const [sortedContactList, setSortedContactList] = useState([]);
    const [searchContact, setSearchContact] = useState([]);
    const setsidebar = (newarray) => {
        setSortedContactList(newarray);
        setSearchContact(newarray);
    }
    return (
        <ChatContext.Provider
            value={{
                selectedPerson,
                setSelectedPerson,
                user,
                setUser,
                sortedContactList,
                setsidebar,
                searchContact,
                setSearchContact
            }}
        >
            {children}
        </ChatContext.Provider>
    );
};
const useAppContext = () => {
    return useContext(ChatContext);
};

export { ChatProvider, useAppContext };

