import { useRef } from 'react';
import '../chat.css';


function Searchside({ setSearchContact }) {

    const searchContact = useRef(null);


    const handleEnter = (e) => {
        if (e.key === 'Enter') {
            e.stopPropagation();
        }
    };
    const search = function (e) {
        e.stopPropagation(); // Prevent the form submission from refreshing the page
        // const searchQuery = searchContact.current.value;
        setSearchContact(searchContact.current.value)
    }
    return (
        <div className="sidebar-search-container">
            <label htmlFor="search-input">
                <button type="button" id="search-input" onClick={search}>
                    <img src="./Photos/search_icon.png" alt=""></img>
                </button>
                <input ref={searchContact} onKeyUp={search} onKeyDown={handleEnter}
                    type="text" name="q" id="search-input" placeholder="Search contact"
                    autoComplete="off" />
            </label>
        </div>
    );
}

export default Searchside;