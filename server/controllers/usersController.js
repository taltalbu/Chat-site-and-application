import { createUser, verifyUserAndgenerateUser } from "../services/usersServices.js"
export const onCreateUser = async (req, res) => {
    try {
        const { username, displayName, password, profilePic } = req.body;
        const user = { username, displayName, password, profilePic }
        const result = await createUser(user);
        return res.json({ message: result.message });
    } catch (ex) {
        if (ex.message === "Username already exists") {
            return res.status(409).send( ex.message)
        } else {
            res.status(400).send( ex.message)
        }
    }
}



export const getUserByUsername = async (req, res) => {
    try {
        const { username } = req.params;
        const user = req.user;
        const result = verifyUserAndgenerateUser(username,user);
        return res.json(result)

    } catch (ex) {
        if (ex.message === "Unotorized") {
            return res.status(401).send(ex.message);
        } else {
            return res.status(400).send(ex.message);
        }
    }
}
