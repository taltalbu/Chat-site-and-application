import mongoose from 'mongoose';
import {NewUserDetails} from './usersModels.js'
const Schema = mongoose.Schema;
// Message Schema
const messageSchema = new Schema({
  created: Date,
  sender: mongoose.Schema.Types.ObjectId,
  content: String
  
});
export const messageModel = mongoose.model('messages', messageSchema);

// Contact Schema
const chatsSchema = new Schema({
  users:[mongoose.Schema.Types.ObjectId],
  messages:[messageSchema],

});

export const chatsModel = mongoose.model('chats', chatsSchema);


