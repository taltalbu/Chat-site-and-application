export const myMap = new Map();

export const addToMap = (username,socket)=> {
    for (const [key, value] of myMap.entries()) {
        if (value === socket) {
          myMap.delete(key);
        }
      }
    myMap.set(username,socket);
}
export const removeFromMapSocket = (username)=> {
    myMap.delete(username);
}

export const findUserInMapSocket = (username) => {
    return myMap.has(username);
}

export const getMessagesSocket = (data) => {
        var username = data.to;
        const socket = myMap.get(username);
        socket.emit("msg-recive", data, function (ack) {
            if (ack) {
                // Emission successful
                console.log(data.from, "msg to ", username, "from phone successfully");
            } else {
                // Emission failed
                console.log(data.from, "msg to ", username, "from phone failed");
            }
        });
}

export const getNewContactSocket = (data) => {
    var username = data.to;
    const socket = myMap.get(username);
    socket.emit("add-as-contact", data.chat, function (ack) {
        if (ack) {
            // Emission successful
            console.log(data.from, "add as contact", username);
        } else {
            // Emission failed
            console.log(data.from, "add as contact", username);
        }
    })
}