import '../chat.css';
import { useState } from 'react';
import emojiList from './emoji';
function WriteMessage({ addMessage, inputRef }) {
    const [emojiPickerVisible, setEmojiPickerVisible] = useState(false);

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            handleSubmit(event);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const msg = inputRef.current.value.trim();
        if (msg !== '') {
            await addMessage(msg);
            inputRef.current.value = '';
            inputRef.current.focus();
        }
    };

    const handleEmojiSelect = (emoji) => {
        inputRef.current.value += emoji
        inputRef.current.focus();
    };

    const toggleEmojiPicker = () => {
        setEmojiPickerVisible((prevEmojiPickerVisible) => !prevEmojiPickerVisible);
    };

    return (
        <div className="message-footer">
            <div id="form-message">
                <input
                    ref={inputRef}
                    type="text"
                    onKeyDown={handleKeyDown}
                    className="input-send"
                    id="send-messager"
                    placeholder={`Type your message..`}
                    autoComplete="off"
                />

                <button onClick={toggleEmojiPicker} type="button" style={{ minWidth: '20px' }} className="btn-toggle-emoji-picker">
                    <img src="./Photos/emoji.png" alt="" />
                </button>

                <div className={`emoji-picker ${emojiPickerVisible ? 'visible' : ''}`}>
                    {emojiList.map((emoji, index) => (
                        <span key={index} onClick={() => handleEmojiSelect(emoji)}>
                            {emoji}
                        </span>
                    ))}
                </div>
                <button onClick={handleSubmit} type="submit" className="btn-submit-send">
                    <img src="./Photos/send.svg" alt="" />
                </button>

            </div>
        </div>
    );
}

export default WriteMessage;