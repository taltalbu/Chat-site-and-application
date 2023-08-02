import {NewUserLoginModel} from '../models/usersModels.js'
import jwt from 'jsonwebtoken'
import {addToMap} from '../firebase.js'
const getToken = async (req, res) => {
    try {
        let {username,password}=req.body
        const user = await NewUserLoginModel.findOne({username})
        if(user &&(user.password === password)) {
            if(req.headers.phonetoken) {
                addToMap(username,req.headers.phonetoken);
            }
            const token = jwt.sign({ username }, process.env.JWT_SECRET);
            return res.send(token);
        } else {
            return res.status(404).json({message:"username or password incorrect"});
        }
    } catch(ex) {
        return res.status(400).json({message:ex.message});
    }
}

export default {getToken};