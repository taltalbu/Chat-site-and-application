import admin from "firebase-admin"

export const myMap = new Map();


export const addToMap = (username,token)=> {
  for (const [key, value] of myMap.entries()) {
    if (value === token) {
      myMap.delete(key);
    }
  }
  myMap.set(username,token);
}



export const findUserInMap = (username) => {
    const isExist = myMap.has(username);
    if (isExist) {
        return true;
    } else {
        return false;
    }
}


export const addMsg = async (msg, usertoSend,chatID) => {
    const stringifiedMsg = {
      chatID: String(chatID),
      created: msg.created,
      content: String(msg.content),
      username: String(msg.sender.username),
      displayName: String(msg.sender.displayName),
    };
  
    const message = {
      notification: {
        title: 'add-message',
        body: stringifiedMsg.content,
      },
      data: {
        msg: JSON.stringify(stringifiedMsg),
        //sender:JSON.stringify()
      },
      token: myMap.get(usertoSend)
    };
  
    try {
  
      const response = await admin.messaging().send(message);
      console.log('Successfully sent message:', response);
    } catch (error) {
      console.log('Error sending message:', error);
      // Handle the error or throw it to be caught by the caller
      throw error;
    }
  };
  

export const addContact = async (userToNotify,userDisplayName) => {
  console.log("userDisplayName", userDisplayName);
  const stringifiedContact = {
    displayName: String(userDisplayName),

  };
    const contact = {
        notification: {
            title: 'add-contact',
            body: 'reload contact',
        },
        data: {
          displayName: JSON.stringify(stringifiedContact)
        },
        token: myMap.get(userToNotify)
    };
    try {
  
      const response = await admin.messaging().send(contact);
      console.log('Successfully sent contact:', response);
    } catch (error) {
      console.log('Error sending contact:', error);
      // Handle the error or throw it to be caught by the caller
      throw error;
    }
}