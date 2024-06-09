import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useSWR from "swr";
import "../../styles/list-book-style.css";
import { FaStar } from "react-icons/fa";
import FilterComponent from "./FilterComponet";
import { Skeleton } from "@mui/material";

export default function ListBooksComponent() {
  const [books, setBooks] = useState([]);
  const navigate = useNavigate();
  const [state, setState] = useState(false);
  const [selectedAuthor, setSelectedAuthor] = useState("");
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedGenre, setSelectedGenre] = useState("");
  const [authors, setAuthors] = useState([]);
  const [genres, setGenres] = useState([]);
  useEffect(() => {
    async function fetchAuthors() {
      const response = await fetch(`http://localhost:8081/author/all`);
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setAuthors(fetchedItems);
    }
    fetchAuthors();
  }, []);
  useEffect(() => {
    async function fetchGenre() {
      const response = await fetch(`http://localhost:8081/genre/all`);
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setGenres(fetchedItems);
    }
    fetchGenre();
  }, []);
  const user = JSON.parse(localStorage.getItem("user"));
  useEffect(() => {
    async function fetchBook() {
      const response = await fetch(`http://localhost:8081/book/all`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setBooks(fetchedItems);
      console.log(JSON.stringify(fetchedItems));
    }
    fetchBook();
  }, [state]);

  function viewBook(id) {
    navigate(`/book/${id}`);
  }
  function updateBook(id) {
    navigate(`/book/update/${id}`);
  }
  async function deleteBook(id) {
    const response = await fetch(`http://localhost:8081/book/${id}`, {
      method: "DELETE",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(),
    });
  }

  async function saveBookInMyLibrary(id) {
    const status = "To Read";
    const response = await fetch(
      `http://localhost:8081/myLibrary/${id}/${status}`,
      {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    setState(!state);
  }
  console.log(JSON.stringify(authors));
  console.log(JSON.stringify(books));
  console.log(JSON.stringify(genres));
  const isAdmin = user && user.role && user.role.roleName === "admin";
  const isAuth = user.id;
  return (
    <div className="row">
      {books && authors && genres ? (
        <FilterComponent
          books={books}
          authors={authors}
          genres={genres}
          setSelectedAuthor={setSelectedAuthor}
          setSelectedBook={setSelectedBook}
          setSelectedGenre={setSelectedGenre}
        />
      ) : (
        <div>
          <Skeleton />
          <Skeleton animation="wave" />
          <Skeleton animation={false} />{" "}
        </div>
      )}

      <table id="books-table" className="table">
        <thead>
          <tr>
            <th className="th-first">Title</th>
            <th>Author</th>
            <th>Book</th>
            <th>Genre</th>
            <th className="th-second">Action</th>
          </tr>
        </thead>
        <tbody>
          {books ? (
            books.map((book) => (
              <tr key={book.id}>
                <td className="table-row-items">
                  <h4>{book.title}</h4>
                  {[...Array(5)].map((_, index) => {
                    const ratingValue = index + 1;
                    return (
                      <FaStar
                        className="star"
                        color={ratingValue <= book.rate ? "#ffc107" : "#e4e5e9"}
                        size={20}
                      />
                    );
                  })}
                  <div>{book.rate.toFixed(2)}</div>
                </td>
                <td className="table-row-items">
                  <h4>
                    {book.authors.map((author) => (
                      <div key={author.id}>
                        <div>{author.fName}</div>
                      </div>
                    ))}
                  </h4>
                </td>
                <td className="table-row-items">
                  {book.file && (
                    <img
                      src={"/images/" + book.file}
                      width="200px"
                      height="300px"
                    />
                  )}
                </td>
                <td className="table-row-items">
                  <h4>
                    {book.genres.map((genre) => (
                      <div key={genre.id}>
                        <div>{genre.genreName}</div>
                      </div>
                    ))}
                  </h4>
                </td>
                <td>
                  <div className="button-col">
                    <div className="btn-row">
                      <Button
                        className="main-buttons btn-book"
                        onClick={() => viewBook(book.id)}
                        variant="outline-info"
                      >
                        View
                      </Button>
                    </div>
                    <div className="btn-row">
                      {isAuth ? (
                        <Button
                          className="main-buttons btn-book"
                          onClick={() => saveBookInMyLibrary(book.id)}
                          variant="outline-info"
                        >
                          Add to Read
                        </Button>
                      ) : (
                        <div></div>
                      )}
                    </div>

                    {isAdmin ? (
                      <div>
                        {" "}
                        <div className="btn-row">
                          <Button
                            className="main-buttons btn-book"
                            onClick={() => updateBook(book.id)}
                            variant="outline-info"
                          >
                            Edit
                          </Button>
                        </div>
                        <div className="btn-row">
                          <Button
                            className="main-buttons btn-book"
                            onClick={() => deleteBook(book.id)}
                            variant="outline-info"
                          >
                            Delete
                          </Button>
                        </div>
                      </div>
                    ) : (
                      <div></div>
                    )}
                  </div>
                </td>
              </tr>
            ))
          ) : (
            <div></div>
          )}
        </tbody>
      </table>
    </div>
  );
}
