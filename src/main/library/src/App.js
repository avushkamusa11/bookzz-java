import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import './App.css';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import BookComponent from './Components/Books/BookComponent';
import ListBooksComponent from './Components/Books/ListBooksComponent';
import RegistrationComponent from './Components/Users/RegistrationComponent';
import AddBookComponent from './Components/Books/AddBookComponent';
import UpdateBookComponent from './Components/Books/UpdateBookComponent';
import AddAuthorComponent from './Components/Authors/AddAuthorComponent';
import AddGenreComponent from './Components/Genres/AddGenreComponent';
import ListGenreComponent from './Components/Genres/ListGenreComponent';
import ListAuthorsComponent from './Components/Authors/ListAuthorsComponent';
import LoginComponent from './Components/Users/LoginComponent';
import ErrorComponent from './Components/ErrorComponent';
import HeaderComponent from './Components/HeaderComponent';
import MyLibraryComponent from './Components/CustomLibrary/MyLibraryComponent';
import AddBookToMyLibraryComponent from './Components/CustomLibrary/EditBookFromMyLibraryComponent';
import AddQuoteComponent from './Components/Quotes/AddQuoteComponent';
import ListMyQuotesComponent from './Components/Quotes/ListMyQuotesComponent';
import StatisticComponent from './Components/Statistics/StatisticComponent';
import AddGoalComponent from './Components/Statistics/AddGoalComponent';
import AddProfilePictureComponent from './Components/Users/AddProfilePictureComponent';
import ProfileComponent from './Components/Users/ProfileComponent';
import EditProfileOptionComponent from './Components/Users/EditProfileOptionComponent';
import EditBiogrphyComponent from './Components/Users/EditBiographyComponent';
import ScheduleComponent from './Components/Schedule/ScheduleComponent';
import AllUserComponent from './Components/Users/AllUserComponent';
import FriendsComponent from './Components/Users/FriendsComponent';
import ConversationComponent from './Components/Messages/ConversationComponent';
import ReadBookComponent from './Components/Books/ReadBookComponent';
import HomeComponent from './Components/HomeComponent';



function App() {

  const[refresh,setRefresh] = useState(false);
  function onReRender () {
   setRefresh(!refresh);
   
 } 
 const user = JSON.parse(localStorage.getItem('user'));
 console.log(JSON.stringify(user));
  const isAdmin = (user  && user.role && user.role.roleName === "admin");
  const isAuth = (user && user.id);
  return (
    <div>
      <Router>
        <div>
          <HeaderComponent refresh={refresh} onReRender={onReRender} />
            <div className="container">
              <Routes>
                <Route path = "/book/:id" exact  element={<BookComponent />}  />
                <Route path='/books' exact element={<ListBooksComponent />} />
                <Route path = "/registration" exact  element={<RegistrationComponent />}  />
                <Route path='/home' exact element={<HomeComponent />} />
                {isAuth ? <Route path = "/myLibrary" element={<MyLibraryComponent />} /> : <Route path = "/myLibrary" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/myLibrary/editBookFromMyLibrary/:id" element={<AddBookToMyLibraryComponent />} /> : <Route path = "/myLibrary/editBookFromMyLibrary/:id" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/quotes/addQuote" element={<AddQuoteComponent />} /> : <Route path = "/quotes/addQuote" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/quotes" element={<ListMyQuotesComponent />} /> : <Route path = "/quotes" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/stats" element={<StatisticComponent />} /> : <Route path = "/stats" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/profilePicture" element={<AddProfilePictureComponent refresh={refresh} onReRender={onReRender}/>} /> : <Route path = "/profilePicture" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/Profile/:id" element={<ProfileComponent />} /> : <Route path = "/Profile:id" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/editProfile" element={<EditProfileOptionComponent />} /> : <Route path = "/ediProfile" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/myGoal" element={<AddGoalComponent />} /> : <Route path = "/myGoal" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/biographyUpdate" element={<EditBiogrphyComponent />} /> : <Route path = "/biographyUpdate" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/allUsers" element={<AllUserComponent />} /> : <Route path = "/allUsers" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/schedule" element={<ScheduleComponent />} /> : <Route path = "/schedule" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/friends" element={<FriendsComponent />} /> : <Route path = "/friends" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/message/:id" element={<ConversationComponent />} /> : <Route path = "/message/:id" element={<ErrorComponent />} />}
                {isAuth ? <Route path = "/read/:id" element={<ReadBookComponent />} /> : <Route path = "/message/:id" element={<ErrorComponent />} />}
              
                {isAdmin ?
                <Route path = "/book/add" element={<AddBookComponent />} /> : <Route path = "/book/add" element={<ErrorComponent />} />}
                 {isAdmin ?
                <Route path = "/book/update/:id" element={<UpdateBookComponent />} />:<Route path = "/book/update/:id" element={<ErrorComponent />} /> }
                {isAdmin ?
                <Route path='/author/add' element={<AddAuthorComponent />} /> : <Route path='/author/add' element={<ErrorComponent />} /> }
                {isAdmin ?
                <Route path='/genre/add' element={<AddGenreComponent />} /> : <Route path='/genre/add' element={<ErrorComponent />} />}
                <Route path='/genres' element={<ListGenreComponent />} />
                <Route path='/authors' element={<ListAuthorsComponent />} />
                <Route path = "/" exact element={<LoginComponent refresh={refresh} onReRender = {onReRender}/>} />
              </Routes>
            </div>
            </div>
      </Router>
    </div>
  );
}

export default App;
