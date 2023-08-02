import {onGetContact, onAddContact,onSendMsg,onGetMessages,onDeleteContact,onGetChat} from '../controllers/chatController.js'
import {protect} from '../middleware/authMiddleware.js'
import express from 'express'
const router = express.Router();
router.route('/').get(protect,onGetContact);
router.route('/').post(protect,onAddContact);
router.route('/:id').get(protect,onGetChat)
router.route('/:id').delete(protect,onDeleteContact)
router.route('/:id/Messages').post(protect,onSendMsg);
router.route('/:id/Messages').get(protect,onGetMessages);
export default router;
