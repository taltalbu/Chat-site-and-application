import jwt from 'jsonwebtoken'
import { NewUserModel } from '../models/usersModels.js';
export const protect = async (req, res, next) => {
    let token;
  
    if (
      req.headers.authorization &&
      req.headers.authorization.startsWith("Bearer")
    ) {
      try {
        token = req.headers.authorization.split(" ")[1];
  
        //decodes token id
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        const user = await NewUserModel.findOne({username:decoded.username});
        if(!user) {
            res.status(404).send("error");
        }
        req.user = user
  
        next();
      } catch (error) {
        res.status(401).send("Not authorized, token failed");
        // throw new Error("Not authorized, token failed");
      }
    }
  
    if (!token) {
      res.status(401).send("Not authorized, no token");
    //   throw new Error("Not authorized, no token");
    }
  };
  