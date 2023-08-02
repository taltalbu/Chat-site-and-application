import express from 'express'
import cors from 'cors'
import userRoute from './routes/usersRouter.js'
import tokenRoutes from './routes/token.js'
import chatRoutes from './routes/chatRoutes.js'
import dotenv from 'dotenv'
import {connectMDB} from './mongo/connect.js'
import {socketIOHandler} from './socket/socket.js'
import http from 'http'
import admin from "firebase-admin";
const serviceAccount = './key.json';

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});





// import chatRoutes from './routes/chat.js'
// Load environment variables from .env file
dotenv.config();
const app = express();
app.use(express.json());
app.use(cors());

app.use(express.urlencoded({
    extended: true
}))
// const server = http.createServer(app);


console.log(process.env.CONNECTION_STRING);
console.log(process.env.PORT);
connectMDB();

const site = (express.static('../public/build'));
app.use("/",site);
app.use("/register",site);
app.use("/chat",site);
app.use('/api/Users', userRoute);
app.use('/api/Tokens', tokenRoutes);
app.use('/api/Chats', chatRoutes);

const server = http.createServer(app);
server.listen(process.env.PORT, () => {
    console.log('Server listening on port 5000');
});
socketIOHandler(server);