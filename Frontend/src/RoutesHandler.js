import { Route, Routes, BrowserRouter } from 'react-router-dom';

import StartPage from './pages/StartPage';
import FileListPage from './pages/FileListPage';
import FileUploadPage from './pages/FileUploadPage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';

export const RoutesHandler = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StartPage />}/>
        <Route path="/list" element={<FileListPage />}/>
        <Route path="/transcribe/file" element={<FileUploadPage />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/signup" element={<SignupPage />}/>
      </Routes>
    </BrowserRouter>
  );
};