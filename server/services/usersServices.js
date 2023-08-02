import { NewUserModel, NewUserLoginModel } from "../models/usersModels.js";
import { getChatById } from "../mongo/db.js";
export const onGetChat = async (id,user)=>{
  const chat = await getChatById(id);
  if(!chat) {
    throw new Error("bad request");
  }
  const isExist = chat.users.find((u)=> u === user);
  if(!isExist) {
    throw new Error("Unotorized");
  }
  chat.users.forEach((u) => {
    delete u._id;
  });
  return chat;
}

export const createUser = async (user) => {
  const { username, displayName, password, profilePic } = user;
  // Check if the username already exists
  const existingUser = await NewUserModel.findOne({ username });
  if (existingUser) {
    throw new Error("Username already exists");
  }


  // Create a new user
  const newUser = new NewUserModel({
    username: username,
    displayName: displayName,
    profilePic: profilePic
  });

  const newUserLogin = new NewUserLoginModel({
    username: username,
    password: password
  })

  await newUserLogin.save();

  // Save the new user
  return await newUser.save();
}


export const verifyUserAndgenerateUser = (username, user) => {
  if (username && user) {
    if (user.username === username) {
      return { username: user.username, displayName: user.displayName, profilePic: user.profilePic }
    } else {
      throw new Error("Unotorized");
    }

  } else {
    throw new Error("error in details");
  }
}


