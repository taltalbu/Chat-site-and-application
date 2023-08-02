import { Server } from 'socket.io';
import {addToMap, removeFromMapSocket} from './mapSocket.js'
export const socketIOHandler = (server) => {
    const io = new Server(server, {
        cors: {
            origin: 'http://localhost:5000',
            credentials: true,
        },
    });
    io.on("connection", (socket) => {
        socket.on("new-user-connection", (userUsername) => {
            console.log(userUsername,"try to connect");
            socket.join(userUsername);
            console.log(userUsername, "connected succesfully");
            addToMap(userUsername, socket)
        }) 
        socket.on("send-msg", (data) => {
            var username = data.to;
            socket.in(username).emit("msg-recive", data, function (ack) {
                if (ack) {
                    // Emission successful
                    console.log(data.from, "msg to ", username, "successfully");
                } else {
                    // Emission failed
                    console.log(data.from, "msg to ", username, " failed");
                }
            });
        })
        socket.on("add-contact", (data) => {
            var username = data.to;
            socket.in(username).emit("add-as-contact", data.chat, function (ack) {
                if (ack) {
                    // Emission successful
                    console.log(data.from, "add as contact", username);
                } else {
                    // Emission failed
                    console.log(data.from, "add as contact", username);
                }
            })
        })
        socket.on("dissconnect", (userUsername) => {
            socket.leave(userUsername);
            removeFromMapSocket(userUsername);
            console.log(userUsername, "discconected");
        })
        socket.on("remove-contact", (data) => {
            const  username  = data.userDeleted;
            socket.in(username).emit("remove-contact",data.chatDeleted, (ack) => {
                if (ack) {
                    // Emission successful
                    console.log(`${data.userDeleteing} deleting chat with ${username}`);
                } else {
                    // Emission failed
                    console.log(`Failed to add ${data.userDeleteing} as a contact for ${username}`);
                }
            });
        });
    
    })

}