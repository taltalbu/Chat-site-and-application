import { NewUserModel } from '../models/usersModels.js'
import { ContactList, getMessages, addChat, addMsg, deleteContactById,getChatById, getUsersChats } from '../mongo/db.js'

export const getContactList = async (user) => {
    try {
        const username = user.username
        const contact = await ContactList(username)
        return contact
    } catch (ex) {
        throw new Error(ex.message);
    }

}

export const getChat = async (id,user)=> {
    if(id && user) {
        try {
            const chat = await getChatById(id);
            const isUserInChat = chat.users.find((u)=>u.username === user.username);
            if(isUserInChat) {
                return chat;
            } else {
                throw new Error("unotorized");
            }
        } catch (ex) {
            throw new Error(ex.message);
        }
    } else {
        throw new Error("bad request");
    }
} 

export const addContactToList = async (usernameToAdd, user) => {
    try {
        
        const isUserExist = await NewUserModel.findOne({ username: usernameToAdd.username });
        if (isUserExist) {
            const newChat= await addChat(user, isUserExist);
            return newChat;
        } else {
            throw new Error("user not found")
        }
    } catch (ex) {
        throw new Error(ex.message);
    }

}

export const addMessage = async (id, msg, user) => {
    try {
        const newMsg = await addMsg(id, user, msg);
        return newMsg;
    } catch (ex) {
        throw new Error(ex.message);
    }
}

export const getMsgs = async (id,user) => {
    try {
        const msgs = await getMessages(id,user);
        return msgs;
    } catch (ex) {
        throw new Error(ex.message);
    }
}
export const getOtherUsers = async (chatID, username) => {
    const users = await getUsersChats(chatID);
    const user1 = await NewUserModel.findById(users[0]);
    const user2 = await NewUserModel.findById(users[1]);
    const otherUser = user1.username !== username ? user1 : user2
    return otherUser;
}
export const deleteContact = async (id,user) => {
    try {
        const result = await deleteContactById(id,user);
        return result;
    } catch (ex) {
        throw new Error(ex.message);
    }
}


