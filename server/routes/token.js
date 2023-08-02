import tokenController from "../controllers/token.js";
import express from 'express'
const router = express.Router();
router.route('/')
    .post(tokenController.getToken);
export default router;