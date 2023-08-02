import mongoose from 'mongoose';
const Schema = mongoose.Schema;

export const NewUserDetails = new Schema ({
    username: {
        type:String,
        required: true,
    },
    displayName: {
        type:String,
        required:true
    },
    profilePic: {
        type: String    // Store the content type (e.g., image/png, image/jpeg)
    }
})

export const NewUserModel = mongoose.model('usersdetails', NewUserDetails);

const NewUserLogin = new Schema ({
    username: {
        type:String,
        required: true,
        unique:true
    },
    password: {
        type:String,
        required:true
    },
})

export const NewUserLoginModel = mongoose.model('userslogins', NewUserLogin);
