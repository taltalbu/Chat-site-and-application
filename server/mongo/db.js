import { messageModel, chatsModel } from '../models/chatModels.js'
import { NewUserModel } from '../models/usersModels.js'

const convertSenderIDtoSender = async (chat) =>{
  if(chat.messages.length == 0) {
    return [];
  }
  let messageArr = [];
  const user1 = await NewUserModel.findById(chat.users[0]);
  const user2 = await NewUserModel.findById(chat.users[1]);
  let i = 0;
  for(let msg of chat.messages) {
    let sender = msg.sender.toString() === chat.users[0].toString() ? user1 : user2;
    messageArr.push({
      id:i,
      sender:sender,
      created:msg.created,
      content:msg.content
    });
  };
  return messageArr;
}

export const getChatById =  async (id)=> {
  const chat = await chatsModel.findById(id);
  if(!chat) {
    throw new Error("bad request");
  }
  const user1 = await NewUserModel.findById(chat.users[0]);
  const user2 = await NewUserModel.findById(chat.users[1]);
  const chatObjact = {
    id:chat._id,
    users:[user1,user2],
    messages:await convertSenderIDtoSender(chat)
  }
  return chatObjact;

}
export const getUsersChats = async(chatID) => {
  const chat = await chatsModel.findById(chatID);
  return chat.users;
}
export const addMsg = async (id, sender, msg) => {
    const chat = await chatsModel.findById(id);
    const isInChat = chat.users.find((user)=> user.toString() === sender._id.toString());
    if(!isInChat) {
      throw new Error("unothorized")
    }
    let i = 1;
    if (chat.messages) {
        i = chat.messages.length + 1;
    }
    const newMsg = new messageModel({
        id: i,
        created: new Date(),
        sender: sender._id,
        content: msg
    })
    chat.messages.unshift(newMsg); // Push the new message to the chat's messages array

    // Save the updated chat object
    await chat.save();
    const sendMsgOBJ = {
      id: i,
      created: newMsg.created,
      sender: sender,
      content: msg
    }
    return sendMsgOBJ;

}


export const addChat = async (user1, user2) => {
    const newChat = new chatsModel({
        users: [user1._id,user2._id],
        messages: [],
    })
    await newChat.save();
    const retChat = {
      id:newChat._id,
      user:user2,
      messages:[]
    }
    return retChat;
}


export const ContactList = async (username) => {
    const currentUser = await NewUserModel.findOne({username})

  const chats = await chatsModel.find({ users: currentUser._id });

    if(!chats) {
      return [];
    }
    
    let chatArray = [];
    
    for(let chat of chats)  {
      const otheruserID = chat.users.find(user => user.toString() !== currentUser._id.toString());
      const otherUser = await NewUserModel.findById(otheruserID);
      let lastMessage = null;
  
      if (chat.messages.length !== 0) {
        lastMessage = chat.messages[0];
        lastMessage.sender = NewUserModel.findById(lastMessage.sender);
      }
  
      chatArray.push({
        id: chat._id,
        user: otherUser,
        lastMessage: lastMessage
      });
    };
    // Sort the chatArray by last message and created date
    chatArray.sort((a, b) => {
      if (a.lastMessage && b.lastMessage) {
        return b.lastMessage._id.getTimestamp().getTime() - a.lastMessage._id.getTimestamp().getTime();
      } else if (a.lastMessage && !b.lastMessage) {
        return b.id.getTimestamp().getTime() - a.lastMessage._id.getTimestamp().getTime();;
      } else if (!a.lastMessage && b.lastMessage) {
        return b.lastMessage._id.getTimestamp().getTime() - a.id.getTimestamp().getTime();
      } else {
        return b.id.getTimestamp().getTime() - a.id.getTimestamp().getTime();
      }
    });
    return chatArray;
  };
  



export const getMessages = async (id,user) => {
    const chat = await chatsModel.findById(id);
    if(!chat) {
      throw new Error("bad request")
    }
    const isInChat = chat.users.find((userID)=> userID.toString() === user._id.toString());
    if(!isInChat) {
      throw new Error("unothorized")
    }
    return await convertSenderIDtoSender(chat);
}

export const deleteContactById = async (id, user) => {
  const chat = await chatsModel.findById(id);
  if (!chat) {
    throw new Error("Chat not found");
  }

  const isUserInChat = chat.users.some((userID) => userID.toString() === user._id.toString());

  if (!isUserInChat) {
    throw new Error("Unauthorized");
  }

  // Delete the chat from the database
  await chatsModel.deleteOne({_id:id});

  return "Chat deleted successfully";
};

