import { getContactList, addContactToList, addMessage, getMsgs,deleteContact,getChat,getOtherUsers } from '../services/chatService.js'
import {findUserInMap,addMsg,addContact} from '../firebase.js'
import {findUserInMapSocket, getMessagesSocket, getNewContactSocket} from '../socket/mapSocket.js'
export const  onGetChat = async (req, res) => {
    try {

        const user = req.user
        const { id } = req.params;
        const result = await getChat(id,user);

        return res.json(result);
    } catch (ex) {
        return res.status(400);
    }

}

export const onGetContact = async (req, res) => {
    try {
        const user = req.user
        const result = await getContactList(user);
        res.json(result);
    } catch (ex) {
        res.status(401).send("unotherized");
    }
}
export const onAddContact = async (req, res) => {
    try {
        const user = req.user
        const username = req.body;
        if (username && user) {
            const newChat = await addContactToList(username, user);
            if(findUserInMap(newChat.user.username)) {
                await addContact(newChat.user.username,newChat.user.displayName);
            }
            if(findUserInMapSocket(username.username)) {

                const {fromphone} = req.body;
                if(fromphone) {
                const data = {
                    to: username.username,
                    from: user.username,
                    chat: newChat
                } 
                getNewContactSocket(data);

                }
            }
            return res.json(newChat);
        } else {
            return res.status(400).send("Error");
        }
    } catch (ex) {
        return res.status(400).send(ex.message);
    }
}

export const onSendMsg = async (req, res) => {
    try {
        const user = req.user
        const { id } = req.params;
        const { msg } = req.body;
        if (user && id && msg) {
            const result = await addMessage(id, msg, user);
            const otherUser = await getOtherUsers(id,user.username);
            if (findUserInMap(otherUser.username)) {
                await addMsg(result,otherUser.username,id)
            }
            if (findUserInMapSocket(otherUser.username)) {
                const {fromphone} = req.body;
                if(fromphone) {
                    const data = {
                        to : otherUser.username,
                        from : user.username,
                        msg : result,
                        chatID : id
                    }
                    getMessagesSocket(data)
                }
            }
            res.json(result)
        } else {
            res.status(400).send("bad request")
        }
    } catch (ex) {
        res.status(404).send(ex.message)
    }
}

export const onGetMessages = async (req, res) => {
    try {
        const user = req.user
        const { id } = req.params;

        if (user && id) {
            const msgs = await getMsgs(id, user);
            res.json(msgs);
        } else {
            return res.status(401).send("unotherized")
        }
    } catch (ex) {
        return res.status(400).send(ex.message);
    }
}

export const onDeleteContact = async (req, res) => {
    try {
        const user = req.user
        const { id } = req.params;

        if (user && id) {
            const msgs = await deleteContact(id, user);
            res.json(msgs);
        } else {
            return res.status(400).send("bad request")
        }
    } catch (ex) {
        if (ex.message === "Unauthorized") {
            return res.status(401).send(ex.message);
        }
        return res.status(400).send(ex.message);
    }
}