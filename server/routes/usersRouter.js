import {onCreateUser,getUserByUsername} from "../controllers/usersController.js";
import { protect } from "../middleware/authMiddleware.js";
import express from 'express'
const router = express.Router();
router.route('/')
    .post(onCreateUser);
router.route('/:username')
    .get(protect,getUserByUsername);
export default router;