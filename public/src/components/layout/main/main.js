import Login from '../../login/login'
import RegisterPage from '../../register/register'
import Chat from '../../chat/chat'
import PageNotFound from './pageNotfound/PageNotFound'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { ChatProvider } from "../../chat/context/chatProvider.js";
function Main() {
  return (
    <BrowserRouter>
      <Routes >
        <Route exact path="/" element={<Login />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/chat" element={
          <ChatProvider>
            <Chat />
          </ChatProvider>

        } />
        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Main