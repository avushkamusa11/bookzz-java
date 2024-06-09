import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import SubNavComponent from "../SubNavComponent";
import { FaStar } from "react-icons/fa";

export default function MyLibraryComponent() {
  const [myBooks, setMyBooks] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    async function fetchBooks() {
      const response = await fetch(`http://localhost:8081/myLibrary/myBooks`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setMyBooks(fetchedItems);
    }
    fetchBooks();
  }, []);
  console.log(JSON.stringify(myBooks));
  if (myBooks) {
    return (
      <div className="row">
        <SubNavComponent />
        <table id="books-table" className="table">
          <thead>
            <tr>
              <th className="th-first">Title</th>
              <th>Author</th>
              <th>Book</th>
              <th>Genre</th>
              <th>Status</th>
              <th className="th-second">Action</th>
            </tr>
          </thead>
          <tbody>
            {myBooks.map((book) => (
              <tr key={book.id}>
                <td className="table-row-items">
                  <h4>{book.book.title}</h4>
                  {[...Array(5)].map((_, index) => {
                    const ratingValue = index + 1;
                    return (
                      <FaStar
                        className="star"
                        color={
                          ratingValue <= book.book.rate ? "#ffc107" : "#e4e5e9"
                        }
                        size={20}
                      />
                    );
                  })}
                  <div>{book.book.rate.toFixed(2)}</div>
                </td>
                <td className="table-row-items">
                  <h4>
                    {book.book.authors.map((author) => (
                      <div key={author.id}>
                        <div>{author.fName}</div>
                      </div>
                    ))}
                  </h4>
                </td>
                <td className="table-row-items">
                  <img
                    src={"/images/" + book.book.file}
                    width="200px"
                    height="300px"
                  />
                </td>
                <td className="table-row-items">
                  <h4>
                    {book.book.genres.map((genre) => (
                      <div key={genre.id}>
                        <div>{genre.genreName}</div>
                      </div>
                    ))}
                  </h4>
                </td>
                <td className="table-row-items">
                  <h4>{book.status}</h4>
                </td>
                <td>
                  <Button
                    className="main-buttons btn-book"
                    onClick={() =>
                      navigate(`editBookFromMyLibrary/${book.book.id}`)
                    }
                  >
                    EDIT
                  </Button>
                  <Button
                    className="main-buttons btn-book"
                    onClick={() => navigate(`/book/${book.book.id}`)}
                  >
                    VIEW
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}
